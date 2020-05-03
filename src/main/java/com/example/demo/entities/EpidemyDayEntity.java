package com.example.demo.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class EpidemyDayEntity {

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
        EpidemyDayEntity epidemyDayEntity = (EpidemyDayEntity) o;
        return
                Objects.equals(date, epidemyDayEntity.date) &&
                Objects.equals(confirmed, epidemyDayEntity.confirmed) &&
                Objects.equals(recovered, epidemyDayEntity.recovered) &&
                Objects.equals(deaths, epidemyDayEntity.deaths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, confirmed, recovered, deaths);
    }
}
