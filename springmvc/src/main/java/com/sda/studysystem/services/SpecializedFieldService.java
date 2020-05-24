package com.sda.studysystem.services;

import com.sda.studysystem.models.SpecializedField;

import java.util.List;

/**
 * Service to handle SpecializedField related operations
 *
 * @author VinodJohn
 */

public interface SpecializedFieldService {

    /**
     * To create a new SpecializedField
     *
     * @param specializedField SpecializedField
     */
    boolean createSpecializedField(SpecializedField specializedField);

    /**
     * To update an existing SpecializedField
     *
     * @param specializedField specializedField
     * @return update result
     */
    boolean updateSpecializedField(SpecializedField specializedField);

    /**
     * To get a specializedField by its id
     *
     * @param specializedFieldId id of a specializedField
     * @return SpecializedField
     */
    SpecializedField getById(Long specializedFieldId);

    /**
     * To get all the specializedFields
     *
     * @return list of all specializedFields
     */
    List<SpecializedField> getAllSpecializedFields();

    /**
     * Delete specializedField(change active state) by Id
     *
     * @param specializedFieldId specializedFieldId
     * @return is it deleted
     */
    boolean deleteSpecializedFieldById(Long specializedFieldId);

    /**
     * Restore specializedField(change active state) by Id
     *
     * @param specializedFieldId specializedFieldId
     * @return is it restored
     */
    boolean restoreSpecializedFieldById(Long specializedFieldId);
}


