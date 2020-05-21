package com.sda.studysystem.controllers;

import com.sda.studysystem.models.School;
import com.sda.studysystem.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping("")
    public String showAllSchool(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                    Model model) {
        List<School> schools = schoolService.getAllSchools();
        model.addAttribute("schools", schools);
        return "school/school-list";
    }

    @GetMapping("/add")
    public String addSchoolForm(@ModelAttribute("school") School school, @ModelAttribute("messageType") String messageType,
                                  @ModelAttribute("message") String message) {
        return "school/school-add";
    }

    @PostMapping("/add")
    public String addSchool(School school, RedirectAttributes redirectAttributes) {
        boolean createResult = false;

        if (isSchoolValid(school)) {
            school.setActive(true);
            createResult = schoolService.createSchool(school);
        }

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school/";
        } else {
            redirectAttributes.addFlashAttribute("school", school);
            redirectAttributes.addFlashAttribute("message", "Error in creating a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateCityForm(@PathVariable("id") Long schoolId, @RequestParam(value = "school", required = false) School school,
                                 @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message, Model model) {
        if (school == null) {
            model.addAttribute("school", schoolService.getById(schoolId));
        }

        return "school/school-update";
    }

    @PostMapping("/update/{id}")
    public String updateSchool(@PathVariable("id") Long schoolId, School school, RedirectAttributes redirectAttributes) {
        boolean updateResult = false;

        if (isSchoolValid(school)) {
            school.setId(schoolId);
            updateResult = schoolService.updateSchool(school);
        }

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school/";
        } else {
            redirectAttributes.addAttribute("id", schoolId);
            redirectAttributes.addAttribute("school", school);
            redirectAttributes.addFlashAttribute("message", "Error in updating a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSchool(@PathVariable("id") Long schoolId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = schoolService.deleteSchoolById(schoolId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/school/";
    }

    @GetMapping("/restore/{id}")
    public String restoreSchool(@PathVariable("id") Long schoolId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = schoolService.restoreSchoolById(schoolId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/school/";
    }

    private boolean isSchoolValid(School school) {
        return !school.getName().isEmpty();
    }

}


