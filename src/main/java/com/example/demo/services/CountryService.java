package com.example.demo.services;

import com.example.demo.models.Country;
import com.example.demo.models.EpidemyDay;
import com.example.demo.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Country> findAllCountries(){
        return countryRepository.findAll();
    }
    public Optional<EpidemyDay> findByCountryAndDate(String name, String date) throws ParseException {
        Optional<Country> country = this.findByCountryname(name);
        return country.isPresent() ?
                country.get().getEpidemyDay(parseDateFromApi(date)) : Optional.empty();
    }
    public List<EpidemyDay> findAllDays(){
        return this.findAllCountries().stream()
                .map(Country::getEpidemyDays)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
    public List<EpidemyDay> findAllDaysByCountry(String name){
        return this.findByCountryname(name)
                .stream().parallel()
                .map(Country::getEpidemyDays)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
