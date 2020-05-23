package com.sda.studysystem.controllers;

import com.sda.studysystem.models.SpecializedField;
import com.sda.studysystem.models.Teacher;
import com.sda.studysystem.services.SpecializedFieldService;
import com.sda.studysystem.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Teacher operations
 */

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SpecializedFieldService specializedFieldService;

    @GetMapping("")
    public String showAllTeachers(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                  Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "teacher/teacher-list";
    }

    @GetMapping("/add")
    public String addTeacherForm(@ModelAttribute("teacher") Teacher teacher, @ModelAttribute("messageType") String messageType,
                                @ModelAttribute("message") String message, Model model) {
        List<SpecializedField> specializedFields = specializedFieldService.getAllSpecializedFields().stream()
                .filter(SpecializedField::isActive).collect(Collectors.toList());
        model.addAttribute("specializedFields", specializedFields);
        return "teacher/teacher-add";
    }

    @PostMapping("/add")
    public String addTeacher(@Valid Teacher teacher, RedirectAttributes redirectAttributes) {
        boolean createResult = teacherService.createTeacher(teacher);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "Teacher has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/teacher/";
        } else {
            redirectAttributes.addFlashAttribute("teacher", teacher);
            redirectAttributes.addFlashAttribute("message", "Error in creating a teacher!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/teacher/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateTeacherForm(@PathVariable("id") Long teacherId, @RequestParam(value = "teacher", required = false) Teacher teacher,
                                   @ModelAttribute("messageType") String messageType,
                                   @ModelAttribute("message") String message, Model model) {
        if (teacher == null) {
            model.addAttribute("teacher", teacherService.getById(teacherId));
        }

        List<SpecializedField> specializedFields = specializedFieldService.getAllSpecializedFields().stream()
                .filter(SpecializedField::isActive).collect(Collectors.toList());
        model.addAttribute("specializedFields", specializedFields);
        return "teacher/teacher-update";
    }

    @PostMapping("/update/{id}")
    public String updateTeacher(@PathVariable("id") Long teacherId, @Valid Teacher teacher, RedirectAttributes redirectAttributes) {
        teacher.setId(teacherId);
        boolean updateResult = teacherService.updateTeacher(teacher);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "Teacher #" + teacherId + "has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/teacher/";
        } else {
            redirectAttributes.addAttribute("id", teacherId);
            redirectAttributes.addAttribute("teacher", teacher);
            redirectAttributes.addFlashAttribute("message", "Error in updating a teacher #" + teacherId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/teacher/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long teacherId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = teacherService.deleteTeacherById(teacherId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "Teacher #" + teacherId + "has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a teacher #" + teacherId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/teacher/";
    }

    @GetMapping("/restore/{id}")
    public String restoreTeacher(@PathVariable("id") Long teacherId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = teacherService.restoreTeacherById(teacherId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "Teacher #" + teacherId + " has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a teacher #" + teacherId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/teacher/";
    }

}
