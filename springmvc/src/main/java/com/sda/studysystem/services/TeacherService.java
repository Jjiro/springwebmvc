package com.sda.studysystem.services;

import com.sda.studysystem.models.Teacher;

import java.util.List;

/**
 * Service to handle Teacher related operations
 *
 * @author VinodJohn
 */

public interface TeacherService {

    /**
     * To create a new Teacher
     *
     * @param teacher Teacher
     */
    boolean createTeacher(Teacher teacher);

    /**
     * To update an existing Teacher
     *
     * @param teacher teacher
     * @return update result
     */
    boolean updateTeacher(Teacher teacher);

    /**
     * To get a teacher by its id
     *
     * @param teacherId id of a teacher
     * @return Teacher
     */
    Teacher getById(Long teacherId);

    /**
     * To get all the teachers
     *
     * @return list of all teachers
     */
    List<Teacher> getAllTeachers();

    /**
     * Delete teacher(change active state) by Id
     *
     * @param teacherId teacherId
     * @return is it deleted
     */
    boolean deleteTeacherById(Long teacherId);

    /**
     * Restore teacher(change active state) by Id
     *
     * @param teacherId teacherId
     * @return is it restored
     */
    boolean restoreTeacherById(Long teacherId);
}
