package com.sda.studysystem.repositories;

import com.sda.studysystem.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Respository for City
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
