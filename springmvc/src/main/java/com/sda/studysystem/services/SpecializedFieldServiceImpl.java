package com.sda.studysystem.services;

import com.sda.studysystem.models.SpecializedField;
import com.sda.studysystem.models.SpecializedField;
import com.sda.studysystem.repositories.SpecializedFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of SpecializedFieldService
 *
 * @author VinodJohn
 */

@Service
public class SpecializedFieldServiceImpl implements SpecializedFieldService {
    @Autowired
    private SpecializedFieldRepository specializedFieldRepository;

    @Override
    public boolean createSpecializedField(SpecializedField specializedField) {
        if (specializedField == null) {
            return false;
        }

        specializedField.setActive(true);
        specializedFieldRepository.save(specializedField);
        return true;
    }

    @Override
    public boolean updateSpecializedField(SpecializedField specializedField) {
        if (specializedField == null || !specializedFieldRepository.existsById(specializedField.getId())) {
            return false;
        }

        specializedFieldRepository.saveAndFlush(specializedField);
        return true;
    }

    @Override
    public SpecializedField getById(Long specializedFieldId) {
        return specializedFieldRepository.getOne(specializedFieldId);
    }

    @Override
    public List<SpecializedField> getAllSpecializedFields() {
        return specializedFieldRepository.findAll();
    }

    @Override
    public boolean deleteSpecializedFieldById(Long specializedFieldId) {
        SpecializedField specializedField = getById(specializedFieldId);
        if (specializedFieldId == null) {
            return false;
        }

        specializedField.setActive(false);
        updateSpecializedField(specializedField);
        return true;
    }

    @Override
    public boolean restoreSpecializedFieldById(Long specializedFieldId) {
        SpecializedField specializedField = getById(specializedFieldId);
        if (specializedFieldId == null) {
            return false;
        }

        specializedField.setActive(true);
        return updateSpecializedField(specializedField);
    }
}

