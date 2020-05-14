package com.example.demo.data;

import com.example.demo.models.Country;
import com.example.demo.models.EpidemyDay;
import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataUtils {
    private static final String format = "MM/dd/yyyy";
    public static HashMap<String, ArrayList<Integer>> toMap(List<String> list){
        System.out.println(list);
        HashMap<String,ArrayList<Integer>> ret = new HashMap<>();
        list.forEach(line ->{
            if(line.contains("Country/Region"))return;
            String[] temp = parseLine(line).toArray(new String[0]);
            if(!ret.containsKey(temp[0])){
                ArrayList<Integer> newlist = new ArrayList<>();
                for(int i = 3; i<temp.length;i++){
                    newlist.add(Integer.parseInt(temp[i]));
                }
                ret.put(temp[0],newlist);
            }
            else{
                for(int i = 3; i<temp.length;i++){
                    ArrayList<Integer> intlist = ret.get(temp[0]);
                    intlist.add(i,intlist.get(i) + Integer.parseInt(temp[i]));
                }
            }
        });
        return ret;
    }

    private static ArrayList<String> parseLine(String line)
    {
        System.out.println(line);
        ArrayList<String> ret;

        if(line.contains("\""))
        {
            ret = new ArrayList<>();
            int next;
            boolean deleteQuote = false;
            while(line.contains(","))
            {

                if(line.charAt(0)=='"')
                {
                    next=line.indexOf('"',1)+1;
                    deleteQuote=true;
                    System.out.println();
                }
                else
                {
                    next=line.indexOf(',');
                }
                String temp = line.substring(0,next);
                if(deleteQuote)
                {
                    temp=temp.replaceAll("[\"]","");
                    deleteQuote=false;
                }
                ret.add(temp);
                System.out.println(temp);
                line=line.substring(next+1);
            }
            ret.add(line);
        }
        else ret=new ArrayList<>(Arrays.asList(line.split(",")));
        return ret;
    }

    public static ArrayList<String> fixList(List<String> list){
        ArrayList<String> ret = new ArrayList<>();
        list.forEach(elem -> {
            if (elem.charAt(0) == '"') {
                int i = 1;
                while(elem.charAt(i) != '"'){
                    i++;
                }
                String cut = elem.substring(i + 2);
                ret.add(cut);
            }
            else {
                int i = 0;
                while (elem.charAt(i) != ',') {
                    i++;
                }
                String cut = elem.substring(i + 1);
                ret.add(cut);
            }
        });
        return ret;
    }
    public static ArrayList<Date> getDays(List<String> data) throws ParseException {
        String[] line = data.get(0).split(",");
        System.out.println(line);
        String[] ret = Arrays.copyOfRange(line,3,line.length);
        ArrayList<Date> dates = new ArrayList<>();
        for (String s : ret) {
            System.out.println(s);
            dates.add(new SimpleDateFormat(format).parse(fixDate(s)));
        }
        return dates;
    }
    private static String fixDate(String date){
        String[] splitted = date.split("/");
        System.out.println("###########################################################");
        System.out.println(date);
        System.out.println("###########################################################");
        for(int i = 0; i < 3;i++) {
            if ( (i == 0 || i == 1) &&splitted[i].length() == 1) {
                String temp = "0";
                splitted[0] = temp.concat(splitted[0]);
            }
            else if(i==2){
                String temp = "20";
                splitted[i] = temp.concat(splitted[i]);
            }
        }
        return splitted[0]+"/"+splitted[1]+"/"+splitted[2];
    }
    public static HashMap<String,ArrayList<Pair<Date,Integer>>>
    getCountryDaysMap(HashMap<String,ArrayList<Integer>> map, ArrayList<Date> days){

        HashMap<String,ArrayList<Pair<Date,Integer>>> ret = new HashMap<>();
        map.forEach((key,value)->{
            ArrayList<Pair<Date,Integer>> pairs = new ArrayList<>();
            for(int i = 0; i < days.size();i++){
                Pair<Date,Integer> p = new Pair<>(days.get(i),value.get(i));
                pairs.add(p);
            }
            ret.put(key,pairs);
        });
        return ret;
    }

    public static ArrayList<Country> mapsToCountriesEntities(HashMap<String,ArrayList<Pair<Date,Integer>>> confirmed,
                                                             HashMap<String,ArrayList<Pair<Date,Integer>>> deaths,
                                                             HashMap<String,ArrayList<Pair<Date,Integer>>> recovered){
        Set<String> countries = confirmed.keySet();
        ArrayList<Country> countryEntities = new ArrayList<>();
        countries.forEach(country -> {
            Country entity = new Country();
            entity.setCountryname(country);
            countryEntities.add(entity);
        });
        countryEntities.forEach(ctr -> {
            String country = ctr.getCountryname();
            HashMap<Date,ArrayList<Integer>> mapped = new HashMap<>();
            ArrayList<Pair<Date,Integer>> countryConfirmed = confirmed.get(country);
            ArrayList<Pair<Date,Integer>> countryDeaths = deaths.get(country);
            ArrayList<Pair<Date,Integer>> countryRecovered = recovered.get(country);
            countryConfirmed.forEach(pair -> {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(0,pair.getValue());
                mapped.put(pair.getKey(),temp);
            });
            countryDeaths.forEach(pair -> {
                mapped.get(pair.getKey()).add(1,pair.getValue());
            });
            countryRecovered.forEach(pair -> {
                mapped.get(pair.getKey()).add(2,pair.getValue());
            });
            mapped.forEach((key,value)->{
                EpidemyDay day = new EpidemyDay();
                day.setDate(key);
                day.setConfirmed(value.get(0));
                day.setDeaths(value.get(1));
                day.setRecovered(value.get(2));
                ctr.getEpidemyDays().add(day);
            });

        });
    return countryEntities;
    }
    public static Date parseDateFromApi(String date) throws ParseException {
        final String format ="yyyy-MM-dd";
        return new SimpleDateFormat(format).parse(date);
    }
}
