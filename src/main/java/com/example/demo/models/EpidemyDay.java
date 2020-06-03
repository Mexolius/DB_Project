package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

//@Embeddable
@Entity
@Table(name = "EPIDEMYDAYS", schema = "WIKTOR", catalog = "")

public class EpidemyDay {

    @Basic
    @Column(name = "DAYDATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic
    @Column(name = "CONFIRMED")
    private int confirmed;
    @Basic
    @Column(name = "RECOVERED")
    private int recovered;
    @Basic
    @Column(name = "DEATHS")
    private int deaths;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "COUNTRYID")
    @JsonBackReference
    private Country country;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpidemyDay epidemyDay = (EpidemyDay) o;
        return
                Objects.equals(date, epidemyDay.date) &&
                Objects.equals(confirmed, epidemyDay.confirmed) &&
                Objects.equals(recovered, epidemyDay.recovered) &&
                Objects.equals(deaths, epidemyDay.deaths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, confirmed, recovered, deaths);
    }
}
