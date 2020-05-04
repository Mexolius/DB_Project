package com.example.demo.services;

import com.example.demo.models.Country;
import com.example.demo.models.EpidemyDay;
import com.example.demo.repositories.CountryRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.example.demo.data.DataUtils.parseDateFromApi;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    public CountryService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }
    public Optional<Country> findById(Long id){
        return countryRepository.findById(id);
    }
    public Optional<Country> findByCountryname(String name){
        return countryRepository.findByCountryname(name);
    }
    public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }
    public Optional<EpidemyDay> findByCountryAndDate(String name, String date) throws ParseException {
        Optional<Country> country = this.findByCountryname(name);
        return country.isPresent() ?
                country.get().getEpidemyDay(parseDateFromApi(date)) : Optional.empty();
    }
}
