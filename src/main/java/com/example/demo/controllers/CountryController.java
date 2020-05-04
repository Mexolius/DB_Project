package com.example.demo.controllers;

import com.example.demo.models.Country;
import com.example.demo.models.EpidemyDay;
import com.example.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.example.demo.data.DataUtils.parseDateFromApi;

@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;
    @RequestMapping("/countries")
    public List<Country> getAllCountries(){
        return countryService.getAllCountries();
    }
    @RequestMapping("/countries/{name}")
    public Country getCountry(@PathVariable String name){
        return countryService.findByCountryname(name).orElse(null);
    }
    @RequestMapping("/countries/{name}/{date}")
    public EpidemyDay getEpidemyDay(@PathVariable String name, @PathVariable String date) throws ParseException {
        return countryService.findByCountryAndDate(name,date).orElse(null);
    }
}
