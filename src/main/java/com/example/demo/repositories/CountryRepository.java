package com.example.demo.repositories;

import com.example.demo.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country> findByCountryname(String name);
    @Modifying
    @Query(
            value = "delete from COUNTRIES",
            nativeQuery = true
    )
    void deleteFromCountries();
    @Modifying
    @Query(
            value = "delete from EpidemyDays",
            nativeQuery = true
    )
    void deleteFromEpidemyDays();
}
