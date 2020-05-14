package com.example.demo.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "COUNTRIES", schema = "WIKTOR", catalog = "")
public class Country {
    private long countryid;
    private String countryname;

    private Set<EpidemyDay> epidemyDays = new HashSet<>();

    @ElementCollection
    @JoinTable(name = "EPIDEMYDAYS", joinColumns = @JoinColumn(name = "COUNTRYID"))
    public Set<EpidemyDay> getEpidemyDays(){
        return this.epidemyDays;
    }
    public void setEpidemyDays(Set<EpidemyDay> epidemyDays){
        this.epidemyDays = epidemyDays;
    }

    @Id
    @Column(name = "COUNTRYID")
    @GeneratedValue
    public long getCountryid() {
        return countryid;
    }

    public void setCountryid(long countryid) {
        this.countryid = countryid;
    }

    @Basic
    @Column(name = "COUNTRYNAME", unique = true)
    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Optional<EpidemyDay> getEpidemyDay(Date date){
        return epidemyDays
                .stream()
                .filter(day -> day.getDate().equals(date))
                .findAny();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country that = (Country) o;
        return countryid == that.countryid &&
                Objects.equals(countryname, that.countryname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryid, countryname);
    }
}
