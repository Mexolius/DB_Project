package com.example.demo.repositories;

import com.example.demo.models.Country;
import com.example.demo.models.DataFromDay;
import com.example.demo.models.EpidemyDay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DataRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional findByCountryname(String name){
        TypedQuery q;
        q = getCurrentSession().createQuery("From Country c where c.countryName =: name",Country.class);
        q.setParameter("name",name);
        return q.getResultStream().findAny();
    }

    public List<Country> findAll(){
        String q = "from Country";
        return getCurrentSession().createQuery(q,Country.class).getResultList();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void save(Country country){
        System.out.println(country.getEpidemyDays().toString());
        country.getEpidemyDays().forEach(x -> System.out.println(x.getCountry().getCountryName()));
        getCurrentSession().save(country);
    }

    public DataFromDay getTotalEpidemySummary(){
       List<Object[]> result = getCurrentSession().createSQLQuery("select * from (\n" +
                "                  select DAYDATE as \"date\", sum(CONFIRMED) as confirmed, sum(DEATHS) as deaths, sum(RECOVERED) as recovered\n" +
                "                  from EpidemyDays ed\n" +
                "                  group by DAYDATE\n" +
                "                  order by DAYDATE desc\n" +
                "              )\n" +
                "where ROWNUM <= 1").getResultList();
       DataFromDay d = new DataFromDay();
       d.setDate(result.get(0)[0].toString());
       d.setConfirmed(Integer.parseInt(result.get(0)[1].toString()));
       d.setDeaths(Integer.parseInt(result.get(0)[2].toString()));
       d.setRecovered(Integer.parseInt(result.get(0)[3].toString()));
       return d;
    }
    public List<DataFromDay> getEveryDaySummary(){
        List<Object[]> result = getCurrentSession().createSQLQuery("select * from (\n" +
                "                  select DAYDATE as \"date\", sum(CONFIRMED) as confirmed, sum(DEATHS) as deaths, sum(RECOVERED) as recovered\n" +
                "                  from EpidemyDays ed\n" +
                "                  group by DAYDATE\n" +
                "                  order by DAYDATE desc\n" +
                "              )\n").getResultList();
        return result.stream().map(res -> {
            DataFromDay d = new DataFromDay();
            d.setDate(res[0].toString());
            d.setConfirmed(Integer.parseInt(res[1].toString()));
            d.setDeaths(Integer.parseInt(res[2].toString()));
            d.setRecovered(Integer.parseInt(res[3].toString()));
            return d;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFromCountries() {
        getCurrentSession().createNativeQuery("Delete from COUNTRIES").executeUpdate();
    }

    @Transactional
    public void deleteFromEpidemyDays() {
        getCurrentSession().createNativeQuery("Delete from EPIDEMYDAYS").executeUpdate();
    }
}
