package com.sda.studysystem.services;

import com.sda.studysystem.models.County;
import com.sda.studysystem.repositories.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CountyService
 */

@Service
public class CountyServiceImpl implements CountyService {

    @Autowired
    private CountyRepository countyRepository;

    @Override
    public boolean createCounty(County county) {
        if (county == null) {
            return false;
        }
        countyRepository.save(county);
        return true;
    }

    @Override
    public boolean updateCounty(County county) {
        if (county == null || !countyRepository.existsById(county.getId())) {
            return false;
        }

        countyRepository.saveAndFlush(county);
        return true;
    }

    @Override
    public County getById(Long countyId) {
        return countyRepository.getOne(countyId);
    }

    @Override
    public List<County> getAllCounties() {
        return countyRepository.findAll();
    }

    @Override
    public boolean deleteCountyById(Long countyId) {
        County county = getById(countyId);
        if (countyId == null) {
            return false;
        }

        county.setActive(false);
        updateCounty(county);
        return true;
    }

    @Override
    public boolean restoreCountyById(Long countyId) {
        County county = getById(countyId);
        if (countyId == null) {
            return false;
        }

        county.setActive(true);
        return updateCounty(county);
    }
}
