package com.sda.studysystem.services;

import com.sda.studysystem.models.City;
import com.sda.studysystem.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CityService
 *
 * @author VinodJohn
 */

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountyService countyService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private SchoolService schoolService;


    @Override
    public boolean createCity(City city) {
        if (city == null) {
            return false;
        }

        city.setActive(true);
        cityRepository.save(city);
        return true;
    }

    @Override
    public boolean updateCity(City city) {
        if (city == null || !cityRepository.existsById(city.getId())) {
            return false;
        }

        cityRepository.saveAndFlush(city);
        return true;
    }

    @Override
    public City getById(Long cityId) {
        return cityRepository.getOne(cityId);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public boolean deleteCityById(Long cityId) {
        City city = getById(cityId);
        if (cityId == null) {
            return false;
        }

        city.setActive(false);
        updateCity(city);

        schoolService.getAllSchools().stream()
                .filter(school -> school.getCity().getId().equals(cityId))
                .forEach(school -> schoolService.deleteSchoolById(school.getId()));

        return true;
    }

    @Override
    public boolean restoreCityById(Long cityId) {
        City city = getById(cityId);
        if (city == null || !countyService.getById(city.getCounty().getId()).isActive() ||
                !countryService.getById(city.getCountry().getId()).isActive()) {
            return false;
        }

        city.setActive(true);
        updateCity(city);

        schoolService.getAllSchools().stream()
                .filter(school -> school.getCity().getId().equals(cityId))
                .forEach(school -> schoolService.restoreSchoolById(school.getId()));

        return true;
    }
}
