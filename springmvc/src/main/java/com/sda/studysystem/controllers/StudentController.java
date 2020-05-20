package com.sda.studysystem.controllers;


import com.sda.studysystem.models.Student;
import com.sda.studysystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "show-all-students";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        return "add-student";
    }

    @PostMapping("/add")
    public String addStudent(Student student, Model model) {
        student.setActive(true);
        boolean createResult = studentService.createStudent(student);

        if (createResult) {
            model.addAttribute("message", "Student has been successfully created.");
            model.addAttribute("messageType", "success");
            return showAllStudents(model);
        } else {
            model.addAttribute("student", student);
            model.addAttribute("message", "Error in creating a student!");
            model.addAttribute("messageType", "error");
            return addStudentForm(model);
        }
    }

    @GetMapping("/update")
    public String updateStudentForm(Model model) {
        return "update-student";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") Long studentId, Student student, Model model) {
        student.setId(studentId);
        boolean updateResult = studentService.updateStudent(student);

        if (updateResult) {
            model.addAttribute("message", "Student has been successfully updated.");
            model.addAttribute("messageType", "success");
            return showAllStudents(model);
        } else {
            model.addAttribute("student", student);
            model.addAttribute("message", "Error in updating a student!");
            model.addAttribute("messageType", "error");
            return updateStudentForm(model);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId, Model model) {
        boolean deleteResult = studentService.deleteStudentById(studentId);

        if (deleteResult) {
            model.addAttribute("message", "Student has been successfully deleted.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in deleting a student!");
            model.addAttribute("messageType", "error");
        }

        return showAllStudents(model);
    }

    @GetMapping("/restore/{id}")
    public String restoreStudent(@PathVariable("id") Long studentId, Model model) {
        boolean restoreResult = studentService.restoreStudentById(studentId);

        if (restoreResult) {
            model.addAttribute("message", "Student has been successfully restored.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in restoring a student!");
            model.addAttribute("messageType", "error");
        }

        return showAllStudents(model);
    }
}

