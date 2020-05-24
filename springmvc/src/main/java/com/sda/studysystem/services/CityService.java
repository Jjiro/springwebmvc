package com.sda.studysystem.services;

import com.sda.studysystem.models.City;

import java.util.List;

/**
 * Service to handle City related operations
 *
 * @author VinodJohn
 */

public interface CityService {

    /**
     * To create a new City
     *
     * @param city City
     */
    boolean createCity(City city);

    /**
     * To update an existing City
     *
     * @param city city
     * @return update result
     */
    boolean updateCity(City city);

    /**
     * To get a city by its id
     *
     * @param cityId id of a city
     * @return City
     */
    City getById(Long cityId);

    /**
     * To get all the cities
     *
     * @return list of all cities
     */
    List<City> getAllCities();

    /**
     * Delete city(change active state) by Id
     *
     * @param cityId cityId
     * @return is it deleted
     */
    boolean deleteCityById(Long cityId);

    /**
     * Restore city(change active state) by Id
     *
     * @param cityId cityId
     * @return is it restored
     */
    boolean restoreCityById(Long cityId);
}


