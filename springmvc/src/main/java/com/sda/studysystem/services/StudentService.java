package com.sda.studysystem.services;


import com.sda.studysystem.models.Student;

import java.util.List;

/**
 * Service to handle Student related operations
 *
 * @author VinodJohn
 */

public interface StudentService {

    /**
     * To create a new Student
     *
     * @param student Student
     */
    boolean createStudent(Student student);

    /**
     * To update an existing Student
     *
     * @param student student
     * @return update result
     */
    boolean updateStudent(Student student);

    /**
     * To get a student by its id
     *
     * @param studentId id of a student
     * @return Student
     */
    Student getById(Long studentId);

    /**
     * To get all the students
     *
     * @return list of all students
     */
    List<Student> getAllStudents();

    /**
     * Delete student(change active state) by Id
     *
     * @param studentId studentId
     * @return is it deleted
     */
    boolean deleteStudentById(Long studentId);

    /**
     * Restore student(change active state) by Id
     *
     * @param studentId studentId
     * @return is it restored
     */
    boolean restoreStudentById(Long studentId);
}

