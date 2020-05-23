package com.sda.studysystem.controllers;


import com.sda.studysystem.models.Student;
import com.sda.studysystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Student operations
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("")
    public String showAllStudents(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                   Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student/student-list";
    }

    @GetMapping("/add")
    public String addStudentForm(@ModelAttribute("student") Student student, @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message) {
        return "student/student-add";
    }

    @PostMapping("/add")
    public String addStudent(@Valid Student student, RedirectAttributes redirectAttributes) {
        boolean createResult = studentService.createStudent(student);

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

        return "student/student-update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Long studentId, @Valid Student student, RedirectAttributes redirectAttributes) {
        student.setId(studentId);
        boolean updateResult = studentService.updateStudent(student);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "Student #" + studentId + " has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/student/";
        } else {
            redirectAttributes.addAttribute("id", studentId);
            redirectAttributes.addAttribute("student", student);
            redirectAttributes.addFlashAttribute("message", "Error in updating this student #" + studentId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/student/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = studentService.deleteStudentById(studentId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "Student #" + studentId + " has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting student #" + studentId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/student/";
    }

    @GetMapping("/restore/{id}")
    public String restoreStudent(@PathVariable("id") Long studentId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = studentService.restoreStudentById(studentId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "Student #" + studentId + " has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring student #" + studentId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/student/";
    }

}

