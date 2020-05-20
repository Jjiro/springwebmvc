package com.sda.studysystem.controllers;

import com.sda.studysystem.models.Teacher;
import com.sda.studysystem.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for Teacher operations
 */

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public String showAllTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "show-all-teachers";
    }

    @GetMapping("/add")
    public String addTeacherForm(Model model) {
        return "add-teacher";
    }

    @PostMapping("/add")
    public String addTeacher(Teacher teacher, Model model) {
        teacher.setActive(true);
        boolean createResult = teacherService.createTeacher(teacher);

        if (createResult) {
            model.addAttribute("message", "Teacher has been successfully created.");
            model.addAttribute("messageType", "success");
            return showAllTeachers(model);
        } else {
            model.addAttribute("teacher", teacher);
            model.addAttribute("message", "Error in creating a teacher!");
            model.addAttribute("messageType", "error");
            return addTeacherForm(model);
        }
    }

    @GetMapping("/update")
    public String updateTeacherForm(Model model) {
        return "update-teacher";
    }

    @PostMapping("/update/{id}")
    public String updateTeacher(@PathVariable("id") Long teacherId, Teacher teacher, Model model) {
        teacher.setId(teacherId);
        boolean updateResult = teacherService.updateTeacher(teacher);

        if (updateResult) {
            model.addAttribute("message", "Teacher has been successfully updated.");
            model.addAttribute("messageType", "success");
            return showAllTeachers(model);
        } else {
            model.addAttribute("teacher", teacher);
            model.addAttribute("message", "Error in updating a teacher!");
            model.addAttribute("messageType", "error");
            return updateTeacherForm(model);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long teacherId, Model model) {
        boolean deleteResult = teacherService.deleteTeacherById(teacherId);

        if (deleteResult) {
            model.addAttribute("message", "Teacher has been successfully deleted.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in deleting a teacher!");
            model.addAttribute("messageType", "error");
        }

        return showAllTeachers(model);
    }

    @GetMapping("/restore/{id}")
    public String restoreTeacher(@PathVariable("id") Long teacherId, Model model) {
        boolean restoreResult = teacherService.restoreTeacherById(teacherId);

        if (restoreResult) {
            model.addAttribute("message", "Teacher has been successfully restored.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in restored a teacher!");
            model.addAttribute("messageType", "error");
        }

        return showAllTeachers(model);
    }
}
