package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
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
