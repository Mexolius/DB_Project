package com.example.demo.services;

import com.example.demo.data.Stats;
import com.example.demo.models.Country;
import com.example.demo.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.demo.data.DataUtils.*;
import static com.example.demo.data.Stats.*;

@Service
public class CovidDataService {
    private static final String confirmed = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String recovered = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private static final String deaths = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    @Autowired
    private DataRepository countryRepository;

    public List<String> fetchData(Stats stats) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "";
        switch(stats){
            case Deaths:
                url = deaths;
                break;
            case Recovered:
                url = recovered;
                break;
            case Confirrmed:
                url = confirmed;
                break;
        }
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        List<String> lines = Arrays.asList(res.body().split("\n"));
        return fixList(lines);
    }

    private List<Country> getList() throws IOException, InterruptedException, ParseException {
        ArrayList<Date> days = getDays(fetchData(Confirrmed));
        return mapsToCountriesEntities(
                getCountryDaysMap(toMap(fetchData(Confirrmed)),days),
                getCountryDaysMap(toMap(fetchData(Deaths)),days),
                getCountryDaysMap(toMap(fetchData(Recovered)),days));
    }
    @Scheduled(cron = "* * 1 * * *")
    @PostConstruct
    public void addAllDataToDatabase() throws IOException, InterruptedException, ParseException {
        this.countryRepository.deleteFromEpidemyDays();
        this.countryRepository.deleteFromCountries();
        System.out.println("##############################");
        System.out.println("TABLES DROPPED");
        System.out.println("##############################");
        List<Country> list = getList();
        list.forEach(countryRepository::save);
        System.out.println("CRONE ACTIVATED");
    }
}