package com.example.demo.services;

import com.example.demo.models.Country;
import com.example.demo.models.DataFromDay;
import com.example.demo.models.EpidemyDay;
import com.example.demo.repositories.DataRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.data.DataUtils.parseDateFromApi;

@Service
public class CountryService {
    private final DataRepository countryRepository ;

    public CountryService(DataRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    /*public Optional<Country> findById(Long id){
        return countryRepository.findById(id);
    }*/

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

    public DataFromDay getEpidemySummary(){
        return this.countryRepository.getTotalEpidemySummary();
    }

    public List<DataFromDay> getEveryDaySummary() {
        return this.countryRepository.getEveryDaySummary();
    }

    public Optional<EpidemyDay> getCountryDayDifference(String name, String date) throws ParseException {
        Optional<Country> country = this.countryRepository.findByCountryname(name);
        if(country.isEmpty()) return Optional.empty();
        return createDayDifference(country.get(),parseDateFromApi(date));
    }

    public List<EpidemyDay> getAllDaysDifference(String name){
        Optional<Country> country = this.countryRepository.findByCountryname(name);
        if (country.isEmpty()) return new ArrayList<>();
        return country.get().getEpidemyDays()
                .stream()
                .map(day -> {
                    try {
                        return createDayDifference(country.get(), day.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        //return Optional.empty();
                    }
                    return Optional.<EpidemyDay>empty();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<EpidemyDay> createDayDifference(Country country, Date date) throws ParseException {
        Optional<EpidemyDay> current = country.getEpidemyDay(date);
        Optional<EpidemyDay> prev = country.getPreviousEpidemyDay(date);
        if(prev.isEmpty() || current.isEmpty())return Optional.empty();
        else{
            EpidemyDay currDay = current.get();
            EpidemyDay prevDay = prev.get();
            EpidemyDay day = new EpidemyDay();
            day.setDate(currDay.getDate());
            day.setConfirmed(currDay.getConfirmed() - prevDay.getConfirmed());
            day.setDeaths(currDay.getDeaths() - prevDay.getDeaths() > 0 ?
                    currDay.getDeaths() - prevDay.getDeaths() :
                    currDay.getDeaths() + prevDay.getDeaths());
            day.setRecovered(currDay.getRecovered() - prevDay.getRecovered() > 0 ?
                    currDay.getRecovered() - prevDay.getRecovered() :
                    currDay.getRecovered() + prevDay.getRecovered());
            return Optional.of(day);
        }
    }
    public List<String> getCountryNames()
    {
        System.out.println(this.countryRepository.getCountryNames());
        return this.countryRepository.getCountryNames();
    }
}
