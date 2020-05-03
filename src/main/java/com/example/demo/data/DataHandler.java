package com.example.demo.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.demo.data.DataUtils.fixList;
import static com.example.demo.data.DataUtils.toMap;

public class DataHandler {
    private static final String confirmed = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String recovered = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private static final String deaths = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static URL confirmedSource;
    private static URL recoveredSource;
    private static URL deathsSource;

    static {
        try {
            confirmedSource = new URL(confirmed);
            recoveredSource = new URL(recovered);
            deathsSource = new URL(deaths);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getData(Stats stats) throws IOException, IllegalStateException {

        HttpURLConnection connection;
        switch(stats){
            case Confirrmed:
                connection= (HttpURLConnection) confirmedSource.openConnection();
                break;
            case Deaths:
                connection = (HttpURLConnection) deathsSource.openConnection();
                break;
            case Recovered:
                connection = (HttpURLConnection) recoveredSource.openConnection();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stats);
        }
        connection.setConnectTimeout(50000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        return handleFile(connection);
    }
    private static ArrayList<String> handleFile(HttpURLConnection con) throws IOException {
        int status = con.getResponseCode();
        InputStreamReader stream = new InputStreamReader(con.getInputStream());
        BufferedReader reader = new BufferedReader(stream);
        String buffer;
        buffer = reader.readLine();
        ArrayList<String> list = new ArrayList<>();
        while(buffer != null){
            list.add(buffer);
            buffer = reader.readLine();
        }
        reader.close();
        return fixList(list);
    }

    public static HashMap<String,ArrayList<Integer>> getMappedElements(Stats stat) throws IOException {
        return toMap(getData(stat));
    }

}
