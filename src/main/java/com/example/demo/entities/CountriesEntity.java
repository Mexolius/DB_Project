package com.example.demo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "COUNTRIES", schema = "WIKTOR", catalog = "")
public class CountriesEntity {
    private long countryid;
    private String countryname;

    private Set<EpidemyDayEntity> epidemyDays = new HashSet<>();

    @ElementCollection
    @JoinTable(name = "EPIDEMYDAYS", joinColumns = @JoinColumn(name = "COUNTRYID"))
    public Set<EpidemyDayEntity> getEpidemyDays(){
        return this.epidemyDays;
    }
    public void setEpidemyDays(Set<EpidemyDayEntity> epidemyDays){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountriesEntity that = (CountriesEntity) o;
        return countryid == that.countryid &&
                Objects.equals(countryname, that.countryname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryid, countryname);
    }
}
