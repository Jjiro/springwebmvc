package com.sda.studysystem.controllers;


import com.sda.studysystem.models.Category;
import com.sda.studysystem.models.SpecializedField;
import com.sda.studysystem.services.CategoryService;
import com.sda.studysystem.services.SpecializedFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/specialized-field")
public class SpecializedFieldController {

    @Autowired
    private SpecializedFieldService specializedFieldService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String showAllSpecializedFields(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                  Model model) {
        List<SpecializedField> specializedFields = specializedFieldService.getAllSpecializedFields();
        model.addAttribute("specializedFields", specializedFields);
        return "specializedField/specialized-field-list";
    }

    @GetMapping("/add")
    public String addSpecializedFieldForm(@ModelAttribute("specializedField") SpecializedField specializedField,
                                          @ModelAttribute("messageType") String messageType,
                                @ModelAttribute("message") String message, Model model) {
        List<Category> categories = categoryService.getAllCategories().stream()
                .filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categories", categories);
        return "specializedField/specialized-field-add";
    }

    @PostMapping("/add")
    public String addSpecializedField(@Valid SpecializedField specializedField, RedirectAttributes redirectAttributes) {
        boolean createResult = specializedFieldService.createSpecializedField(specializedField);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "SpecializedField has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/specialized-field/";
        } else {
            redirectAttributes.addFlashAttribute("specializedField", specializedField);
            redirectAttributes.addFlashAttribute("message", "Error in creating a SpecializedField!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/specialized-field/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateSpecializedFieldForm(@PathVariable("id") Long specializedFieldId,
                                             @RequestParam(value = "specializedField", required = false) SpecializedField specializedField,
                                   @ModelAttribute("messageType") String messageType,
                                   @ModelAttribute("message") String message, Model model) {
        if (specializedField == null) {
            model.addAttribute("specializedField", specializedFieldService.getById(specializedFieldId));
        }

        List<Category> categories = categoryService.getAllCategories().stream()
                .filter(Category::isActive).collect(Collectors.toList());
        model.addAttribute("categories", categories);
        return "specializedField/specialized-field-update";
    }

    @PostMapping("/update/{id}")
    public String updateSpecializedField(@PathVariable("id") Long specializedFieldId,
                                         @Valid SpecializedField specializedField, RedirectAttributes redirectAttributes) {
        specializedField.setId(specializedFieldId);
        boolean updateResult = specializedFieldService.updateSpecializedField(specializedField);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "SpecializedField #" + specializedFieldId + "has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/specialized-field/";
        } else {
            redirectAttributes.addAttribute("id", specializedFieldId);
            redirectAttributes.addAttribute("specializedField", specializedField);
            redirectAttributes.addFlashAttribute("message", "Error in updating a specializedField #" + specializedFieldId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/specialized-field/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecializedField(@PathVariable("id") Long specializedFieldId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = specializedFieldService.deleteSpecializedFieldById(specializedFieldId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "SpecializedField #" + specializedFieldId + "has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a specializedField #" + specializedFieldId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/specialized-field/";
    }

    @GetMapping("/restore/{id}")
    public String restoreSpecializedField(@PathVariable("id") Long specializedFieldId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = specializedFieldService.restoreSpecializedFieldById(specializedFieldId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "SpecializedField #" + specializedFieldId + " has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a specializedField #" + specializedFieldId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/specialized-field/";
    }

}


