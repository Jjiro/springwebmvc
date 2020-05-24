package com.sda.studysystem.services;

import com.sda.studysystem.models.Category;
import com.sda.studysystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CategoryService
 *
 * @author VinodJohn
 */

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SpecializedFieldService specializedFieldService;

    @Override
    public boolean createCategory(Category category) {
        if (category == null) {
            return false;
        }

        category.setActive(true);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean updateCategory(Category category) {
        if (category == null || !categoryRepository.existsById(category.getId())) {
            return false;
        }

        categoryRepository.saveAndFlush(category);
        return true;
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.getOne(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteCategoryById(Long categoryId) {
        Category category = getById(categoryId);
        if (category == null) {
            return false;
        }

        category.setActive(false);
        updateCategory(category);

        specializedFieldService.getAllSpecializedFields().stream()
                .filter(specializedField -> specializedField.getCategory().getId().equals(categoryId))
                .forEach(specializedField -> specializedFieldService.deleteSpecializedFieldById(specializedField.getId()));

        return true;
    }

    @Override
    public boolean restoreCategoryById(Long categoryId) {
        Category category = getById(categoryId);
        if (category == null) {
            return false;
        }

        category.setActive(true);
        updateCategory(category);

        specializedFieldService.getAllSpecializedFields().stream()
                .filter(specializedField -> specializedField.getCategory().getId().equals(categoryId))
                .forEach(specializedField -> specializedFieldService.restoreSpecializedFieldById(specializedField.getId()));

        return true;
    }
}
