package com.sda.studysystem.services;

import com.sda.studysystem.models.School;
import com.sda.studysystem.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of SchoolService
 *
 * @author VinodJohn
 */

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CountyService countyService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Override
    public boolean createSchool(School school) {
        if (school == null) {
            return false;
        }

        school.setActive(true);
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
        if (school == null) {
            return false;
        }

        school.setActive(false);
        updateSchool(school);
        return true;
    }

    @Override
    public boolean restoreSchoolById(Long schoolId) {
        School school = getById(schoolId);

        if (school == null || !countyService.getById(school.getCounty().getId()).isActive() ||
                !countryService.getById(school.getCountry().getId()).isActive() ||
                !cityService.getById(school.getCity().getId()).isActive()) {
            return false;
        }

        school.setActive(true);
        return updateSchool(school);
    }
}
