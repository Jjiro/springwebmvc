package com.sda.studysystem.services;

import com.sda.studysystem.models.Category;

import java.util.List;

/**
 * Service to handle Category related operations
 *
 * @author VinodJohn
 */

public interface CategoryService {

    /**
     * To create a new Category
     *
     * @param category Category
     */
    boolean createCategory(Category category);

    /**
     * To update an existing Category
     *
     * @param category category
     * @return update result
     */
    boolean updateCategory(Category category);

    /**
     * To get a category by its id
     *
     * @param categoryId id of a category
     * @return Category
     */
    Category getById(Long categoryId);

    /**
     * To get all the categories
     *
     * @return list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Delete category(change active state) by Id
     *
     * @param categoryId categoryId
     * @return is it deleted
     */
    boolean deleteCategoryById(Long categoryId);

    /**
     * Restore category(change active state) by Id
     *
     * @param categoryId categoryId
     * @return is it restored
     */
    boolean restoreCategoryById(Long categoryId);
}


