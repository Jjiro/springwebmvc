package com.sda.studysystem.services;

import com.sda.studysystem.models.School;
import com.sda.studysystem.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of SchoolService
 */

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public boolean createSchool(School school) {
        if (school == null) {
            return false;
        }
        schoolRepository.save(school);
        return true;
    }

    @Override
    public boolean updateSchool(School school) {
        if (school == null || !schoolRepository.existsById(school.getId())) {
            return false;
        }

        schoolRepository.saveAndFlush(school);
        return true;
    }

    @Override
    public School getById(Long schoolId) {
        return schoolRepository.getOne(schoolId);
    }

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public boolean deleteSchoolById(Long schoolId) {
        School school = getById(schoolId);
        if (schoolId == null) {
            return false;
        }

        school.setActive(false);
        updateSchool(school);
        return true;
    }

    @Override
    public boolean restoreSchoolById(Long schoolId) {
        School school = getById(schoolId);
        if (schoolId == null) {
            return false;
        }

        school.setActive(true);
        return updateSchool(school);
    }
}
