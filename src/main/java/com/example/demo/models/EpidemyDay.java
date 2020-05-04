package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class EpidemyDay {

    private Date date;
    private int confirmed;
    private int recovered;
    private int deaths;

    @Basic
    @Column(name = "DAYDATE")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "CONFIRMED")
    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    @Basic
    @Column(name = "RECOVERED")
    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    @Basic
    @Column(name = "DEATHS")
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
