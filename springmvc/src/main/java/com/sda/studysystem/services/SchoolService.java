package com.sda.studysystem.services;

import com.sda.studysystem.models.School;

import java.util.List;

/**
 * Service to handle School related operations
 *
 * @author VinodJohn
 */

public interface SchoolService {

    /**
     * To create a new School
     *
     * @param school School
     */
    boolean createSchool(School school);

    /**
     * To update an existing School
     *
     * @param school school
     * @return update result
     */
    boolean updateSchool(School school);

    /**
     * To get a school by its id
     *
     * @param schoolId id of a school
     * @return School
     */
    School getById(Long schoolId);

    /**
     * To get all the schools
     *
     * @return list of all schools
     */
    List<School> getAllSchools();

    /**
     * Delete school(change active state) by Id
     *
     * @param schoolId schoolId
     * @return is it deleted
     */
    boolean deleteSchoolById(Long schoolId);

    /**
     * Restore school(change active state) by Id
     *
     * @param schoolId schoolId
     * @return is it restored
     */
    boolean restoreSchoolById(Long schoolId);
}

