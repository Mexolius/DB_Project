package com.example.demo.repositories;

import com.example.demo.entities.CountriesEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountriesEntity,Long> {
}
