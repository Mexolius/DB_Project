package com.example.demo.controllers;

import com.example.demo.models.Country;
import com.example.demo.models.DataFromDay;
import com.example.demo.models.EpidemyDay;
import com.example.demo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;


@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @RequestMapping("/countries")
    public List<Country> getAllCountries(){
        return countryService.findAllCountries();
    }

    @RequestMapping("/countries/{name}")
    public Country getCountry(@PathVariable String name){
        return countryService.findByCountryname(name).orElse(null);
    }

    @RequestMapping("/countries/{name}/days/{date}")
    public EpidemyDay getEpidemyDay(@PathVariable String name, @PathVariable String date) throws ParseException {
        return countryService.findByCountryAndDate(name,date).orElse(null);
    }

    @RequestMapping("/countries/{name}/days")
    public List<EpidemyDay> getCountryEpidemyDays(@PathVariable String name){
        return countryService.findAllDaysByCountry(name);
    }

    @RequestMapping("/days")
    public List<EpidemyDay> getAllDays(){
        return countryService.findAllDays();
    }

    @RequestMapping("/summary/current")
    public DataFromDay getSummary(){
        return countryService.getEpidemySummary();
    }

    @RequestMapping("/summary/total")
    public List<DataFromDay> getEveryDaySummary() {return countryService.getEveryDaySummary();}

    @RequestMapping("/countries/{name}/days/difference/{date}")
    public EpidemyDay getCountryDayDifference(@PathVariable String name, @PathVariable String date) throws ParseException {
        return countryService.getCountryDayDifference(name,date).orElse(null);
    }

    @RequestMapping("/countries/{name}/days/difference")
    public List<EpidemyDay> getCountryDaysDifference(@PathVariable String name){
        return countryService.getAllDaysDifference(name);
    }
}
