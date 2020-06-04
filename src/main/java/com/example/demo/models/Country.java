package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COUNTRIES", schema = "WIKTOR", catalog = "")
public class Country {
    @Id
    @Column(name = "COUNTRYID")
    @GeneratedValue
    private long countryId;
    @Basic
    @Column(name = "COUNTRYNAME", unique = true)
    private String countryName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    @JsonManagedReference
    private Set<EpidemyDay> epidemyDays = new HashSet<>();

    public Set<EpidemyDay> getEpidemyDays(){
        return this.epidemyDays;
    }
    public void setEpidemyDays(Set<EpidemyDay> epidemyDays){
        this.epidemyDays = epidemyDays;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Optional<EpidemyDay> getEpidemyDay(Date date){
        return epidemyDays
                .stream()
                .filter(day -> day.getDate().equals(date))
                .findAny();
    }

    public Optional<EpidemyDay> getPreviousEpidemyDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        Date yesterday = calendar.getTime();
        return getEpidemyDay(yesterday);
    }

    public void addDay(EpidemyDay day){
        this.epidemyDays.add(day);
        day.setCountry(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country that = (Country) o;
        return countryId == that.countryId &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName);
    }
}
