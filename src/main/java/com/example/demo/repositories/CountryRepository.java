package com.example.demo.repositories;

import com.example.demo.models.Country;
import com.example.demo.models.DataOnly;
import com.example.demo.models.EpidemyDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country,Long> {
    Optional<Country> findByCountryname(String name);
    @Modifying
    @Query(
            value = "delete from COUNTRIES",
            nativeQuery = true
    )
    void deleteFromCountries();
    @Modifying
    @Query(
            value = "delete from EpidemyDays",
            nativeQuery = true
    )
    void deleteFromEpidemyDays();

    @Query(
            value = "select * from (\n" +
                    "                  select DAYDATE as \"date\", sum(CONFIRMED) as confirmed, sum(DEATHS) as deaths, sum(RECOVERED) as recovered\n" +
                    "                  from EpidemyDays ed\n" +
                    "                  group by DAYDATE\n" +
                    "                  order by DAYDATE desc\n" +
                    "              )\n" +
                    "where ROWNUM <= 1",
            nativeQuery = true
    )
    DataOnly getTotalEpidemySummary();

    @Query(
            value = "select * from (\n" +
                    "                  select DAYDATE as \"date\", sum(CONFIRMED) as confirmed, sum(DEATHS) as deaths, sum(RECOVERED) as recovered\n" +
                    "                  from EpidemyDays ed\n" +
                    "                  group by DAYDATE\n" +
                    "                  order by DAYDATE desc\n" +
                    "              )\n",
            nativeQuery = true
    )
    List<DataOnly> getEveryDaySummary();

}
