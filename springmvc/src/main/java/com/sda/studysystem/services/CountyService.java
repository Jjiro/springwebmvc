package com.sda.studysystem.services;

import com.sda.studysystem.models.County;

import java.util.List;

/**
 * Service to handle County related operations
 *
 * @author VinodJohn
 */

public interface CountyService {

    /**
     * To create a new County
     *
     * @param county County
     */
    boolean createCounty(County county);

    /**
     * To update an existing County
     *
     * @param county county
     * @return update result
     */
    boolean updateCounty(County county);

    /**
     * To get a county by its id
     *
     * @param countyId id of a county
     * @return County
     */
    County getById(Long countyId);

    /**
     * To get all the countys
     *
     * @return list of all countys
     */
    List<County> getAllCounties();

    /**
     * Delete county(change active state) by Id
     *
     * @param countyId countyId
     * @return is it deleted
     */
    boolean deleteCountyById(Long countyId);

    /**
     * Restore county(change active state) by Id
     *
     * @param countyId countyId
     * @return is it restored
     */
    boolean restoreCountyById(Long countyId);
}


