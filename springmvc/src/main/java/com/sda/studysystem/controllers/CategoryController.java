package com.sda.studysystem.controllers;

import com.sda.studysystem.models.Category;
import com.sda.studysystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String showAllCategories(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/category-list";
    }

    @GetMapping("/add")
    public String addCategoryForm(@ModelAttribute("category") Category category, @ModelAttribute("messageType") String messageType,
                              @ModelAttribute("message") String message) {
        return "category/category-add";
    }

    @PostMapping("/add")
    public String addCategory(Category category, RedirectAttributes redirectAttributes) {
        boolean createResult = false;

        if (isCategoryValid(category)) {
            category.setActive(true);
            createResult = categoryService.createCategory(category);
        }

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "Category has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category/";
        } else {
            redirectAttributes.addFlashAttribute("category", category);
            redirectAttributes.addFlashAttribute("message", "Error in creating a category!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/category/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateCityForm(@PathVariable("id") Long categoryId, @RequestParam(value = "category", required = false) Category category,
                                 @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message, Model model) {
        if (category == null) {
            model.addAttribute("category", categoryService.getById(categoryId));
        }

        return "category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long categoryId, Category category, RedirectAttributes redirectAttributes) {
        boolean updateResult = false;

        if (isCategoryValid(category)) {
            category.setId(categoryId);
            updateResult = categoryService.updateCategory(category);
        }

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "Category has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/category/";
        } else {
            redirectAttributes.addAttribute("id", categoryId);
            redirectAttributes.addAttribute("category", category);
            redirectAttributes.addFlashAttribute("message", "Error in updating a category!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/category/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long categoryId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = categoryService.deleteCategoryById(categoryId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "Category has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a category!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/category/";
    }

    @GetMapping("/restore/{id}")
    public String restoreCategory(@PathVariable("id") Long categoryId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = categoryService.restoreCategoryById(categoryId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "City has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a city!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/category/";
    }

    private boolean isCategoryValid(Category category) {
        return !category.getName().isEmpty();
    }
}

