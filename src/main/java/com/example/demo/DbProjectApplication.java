package com.example.demo;

import com.example.demo.entities.CountriesEntity;
import com.example.demo.repositories.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static com.example.demo.data.Stats.*;
import java.util.ArrayList;
import java.util.Date;

import static com.example.demo.data.DataHandler.getData;
import static com.example.demo.data.DataUtils.*;

@SpringBootApplication
public class DbProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CountryRepository rep){
        return (args) ->{
            ArrayList<Date> days = getDays(getData(Confirrmed));
            ArrayList<CountriesEntity> list = mapsToCountriesEntities(
                    getCountryDaysMap(toMap(getData(Confirrmed)),days),
                    getCountryDaysMap(toMap(getData(Deaths)),days),
                    getCountryDaysMap(toMap(getData(Recovered)),days));
            list.forEach(country -> {
                //session.beginTransaction();
                rep.save(country);
                //session.getTransaction().commit();
            });
            System.out.println("test");
        };
    }

}
