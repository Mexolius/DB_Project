package com.example.demo;

import com.example.demo.models.Country;
import com.example.demo.repositories.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;

import static com.example.demo.data.DataHandler.getData;
import static com.example.demo.data.DataUtils.*;
import static com.example.demo.data.Stats.*;

@SpringBootApplication
public class DbProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbProjectApplication.class, args);
    }

   /*@Bean
    public CommandLineRunner demo(CountryRepository rep){
        return (args) ->{
            ArrayList<Date> days = getDays(getData(Confirrmed));
            ArrayList<Country> list = mapsToCountriesEntities(
                    getCountryDaysMap(toMap(getData(Confirrmed)),days),
                    getCountryDaysMap(toMap(getData(Deaths)),days),
                    getCountryDaysMap(toMap(getData(Recovered)),days));
            //session.beginTransaction();
            //session.getTransaction().commit();
            list.forEach(rep::save);
            System.out.println("test");
        };
    }*/

}
