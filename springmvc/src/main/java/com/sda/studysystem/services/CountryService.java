package com.sda.studysystem.services;

import com.sda.studysystem.models.Country;

import java.util.List;

/**
 * Service to handle Country related operations
 *
 * @author VinodJohn
 */

public interface CountryService {

    /**
     * To create a new Country
     *
     * @param country Country
     */
    boolean createCountry(Country country);

    /**
     * To update an existing Country
     *
     * @param country country
     * @return update result
     */
    boolean updateCountry(Country country);

    /**
     * To get a country by its id
     *
     * @param countryId id of a country
     * @return Country
     */
    Country getById(Long countryId);

    /**
     * To get all the countries
     *
     * @return list of all countries
     */
    List<Country> getAllCountries();

    /**
     * Delete country(change active state) by Id
     *
     * @param countryId countryId
     * @return is it deleted
     */
    boolean deleteCountryById(Long countryId);

    /**
     * Restore country(change active state) by Id
     *
     * @param countryId countryId
     * @return is it restored
     */
    boolean restoreCountryById(Long countryId);
}

