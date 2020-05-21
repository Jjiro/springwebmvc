package com.sda.studysystem.controllers;


import com.sda.studysystem.models.Student;
import com.sda.studysystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for Student operations
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/add")
    public String addStudentForm(@ModelAttribute("student") Student student, @ModelAttribute("messageType") String messageType,
                                @ModelAttribute("message") String message) {
        return "student/student-add";
    }

    @PostMapping("/add")
    public String addStudent(Student student, RedirectAttributes redirectAttributes) {
        boolean createResult = false;

        if (isStudentValid(student)) {
            student.setActive(true);
            createResult = studentService.createStudent(student);
        }

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "Student has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/student/";
        } else {
            redirectAttributes.addFlashAttribute("student", student);
            redirectAttributes.addFlashAttribute("message", "Error in creating a student!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/student/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable("id") Long studentId, @RequestParam(value = "student", required = false) Student student,
                                 @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message, Model model) {
        if (student == null) {
            model.addAttribute("student", studentService.getById(studentId));
        }

        return "school/school-update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Long studentId, Student student, RedirectAttributes redirectAttributes) {
        boolean updateResult = false;

        if (isStudentValid(student)) {
            student.setId(studentId);
            updateResult = studentService.updateStudent(student);
        }

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "Student has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/student/";
        } else {
            redirectAttributes.addAttribute("id", studentId);
            redirectAttributes.addAttribute("student", student);
            redirectAttributes.addFlashAttribute("message", "Error in updating a student!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/student/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = studentService.deleteStudentById(studentId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "Student has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a student!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/student/";
    }

    @GetMapping("/restore/{id}")
    public String restoreStudent(@PathVariable("id") Long studentId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = studentService.restoreStudentById(studentId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/student/";
    }

    private boolean isStudentValid(Student student) {
        return !student.getName().isEmpty();
    }

}

