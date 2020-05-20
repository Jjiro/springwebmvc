package com.sda.studysystem.controllers;


import com.sda.studysystem.models.SpecializedField;
import com.sda.studysystem.services.SpecializedFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/specialized-field")
public class SpecializedFieldController {

    @Autowired
    private SpecializedFieldService specializedFieldService;

    @GetMapping("")
    public String showAllSpecializedFields(Model model) {
        List<SpecializedField> specializedFields = specializedFieldService.getAllSpecializedFields();
        model.addAttribute("specializedFields", specializedFields);
        return "show-all-specialized-fields";
    }

    @GetMapping("/add")
    public String addSpecializedFieldForm(Model model) {
        return "add-specialized-field";
    }

    @PostMapping("/add")
    public String addSpecializedField(SpecializedField specializedField, Model model) {
        specializedField.setActive(true);
        boolean createResult = specializedFieldService.createSpecializedField(specializedField);

        if (createResult) {
            model.addAttribute("message", "SpecializedField has been successfully created.");
            model.addAttribute("messageType", "success");
            return showAllSpecializedFields(model);
        } else {
            model.addAttribute("specializedField", specializedField);
            model.addAttribute("message", "Error in creating a specializedField!");
            model.addAttribute("messageType", "error");
            return addSpecializedFieldForm(model);
        }
    }

    @GetMapping("/update")
    public String updateSpecializedFieldForm(Model model) {
        return "update-specializedField";
    }

    @PostMapping("/update/{id}")
    public String updateSpecializedField(@PathVariable("id") Long specializedFieldId, SpecializedField specializedField, Model model) {
        specializedField.setId(specializedFieldId);
        boolean updateResult = specializedFieldService.updateSpecializedField(specializedField);

        if (updateResult) {
            model.addAttribute("message", "SpecializedField has been successfully updated.");
            model.addAttribute("messageType", "success");
            return showAllSpecializedFields(model);
        } else {
            model.addAttribute("specializedField", specializedField);
            model.addAttribute("message", "Error in updating a specializedField!");
            model.addAttribute("messageType", "error");
            return updateSpecializedFieldForm(model);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecializedField(@PathVariable("id") Long specializedFieldId, Model model) {
        boolean deleteResult = specializedFieldService.deleteSpecializedFieldById(specializedFieldId);

        if (deleteResult) {
            model.addAttribute("message", "SpecializedField has been successfully deleted.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in deleting a specializedField!");
            model.addAttribute("messageType", "error");
        }

        return showAllSpecializedFields(model);
    }

    @GetMapping("/restore/{id}")
    public String restoreSpecializedField(@PathVariable("id") Long specializedFieldId, Model model) {
        boolean restoreResult = specializedFieldService.restoreSpecializedFieldById(specializedFieldId);

        if (restoreResult) {
            model.addAttribute("message", "SpecializedField has been successfully restored.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in restoring a specializedField!");
            model.addAttribute("messageType", "error");
        }

        return showAllSpecializedFields(model);
    }
}


