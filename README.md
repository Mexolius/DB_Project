# DB_Project

This is a project for Databases laboratories.
## Technologies

 As a database this project uses Oracle 12c, api is created using Hibernate as a ORM and Springboot as a bootsrapper for it. 

## Classes

### HibernateConf

This class provides hibernate configuration for Spring

Methods:

* sessionFactory: sets datasource and package with models, needed for Autowiring hibernate SessionFactory object
* dataSource: sets oracle database configuration
* hibernateTransactionManager: provides transaction manager, needed for Transactional adnotation.
* hibernateProperties: sets properties of hibernate

### CountryController

  Creates all endpoints in application (see Endpoints for details)

### DataUtils

  Provides helper pethods for parsing data from coronavirus dataset
  
### Country
  
  Entity which represents a Country. Has bidirectional relation with EpidemyDay (One-to-many)
  Fields: 
  * countryID: ID of a country
  * countryName: Name of a country
  
  Methods:
  * Standard getters and setters
  * getPrevoiusEpidemyDay: returns an Optional of EpidemyDay, which can contain previous epidemy day to the given data associated with       this country
  
### DataFromDay:
  
  Similar to EpidemyDay, but contains only data, helper class for mapping native queries which provides api with summaries of days
  
### EpidemyDay:
  
  Entity which represents an EpidemyDay. Has bidirectional relation with EpidemyDay (Many-to-one)
  Fields:
  * date: Date of corresponding day and country
  * confirmed: Amount of total confirmed cases in given country to given date 
  * deaths: Amount of total deaths in given country to date 
  * recovered: Amountn of total recovery cases in given country to given date
  * country: corresponding country
### DataRepository

  Class represents a data repository, it is used for getting data from database to api. 
  
  Methods:
  * findByCountryname: finds a country in database based on its name
  * findAll: finds all countries in database
  * getCurrentSession: returns current session
  * save: saves a country in database
  * getTotalEpidemySummary: returns total number of confirmed, death and recovered cases for most recent day in database
  * getEveryDaySummary: summary as above, but for every day
  * deleteFromCountries: deletes all records from countries
  * deleteFromEpidemyDays: deletes all records from epidemydays
  
 
### CountryService

  Class that represents service which maps data received from database to data needed in endpoints 

### CovidDataService:
  
  Class that fetches data from coronavirus dataset into our database
  Methods:
  fetchData: fetches the data from source
  addAllToDatabase: crone methid (runs every 24h), drops all records from both tables and adds new data

## Endpoints

* /countries

Response: Array of all countries with corresponding epidemy days

Structure:

```
[
  {
    "countryid": 1,
    "countryname":"Benin",
    "epidemyDays":[{...}]
  },
  ...
]

```

* /countries/{name}

Response: Structure as above, but only one country with given name instead of an array.

* /countries/{name}/days/{date}

Response: Data from given date in a given country

Structure:
```
{
  "date":"2020-04-26",
  "confirmed":11617,
  "recovered":2265,
  "deaths":535
}

```
* /countries/{name}/days

Response: Returns all days for a given country
Structure: as above, but array of those elements

* /days
Response: Returns all days (but its sloow)

Structure: as above

* /summary/current

Response: returns summary for today's date (data summed up for each country)
Structure:
```
{
  "date":"2020-05-14T22:00:00.000+0000",
  "recovered":1490928,
  "deaths":229997,
  "confirmed":3915831
}
```
* /summary/total
Response: summary (as above) for each day
Structure: As above, but array of elements

* /countries/{name}/days/difference/{date}
Response: increase/decrease of data compared with previous day
Structure:
```
{
  "date":"2020-03-20",
  "confirmed":70,
  "recovered":0,
  "deaths":0
}
```
* /countries/{name}/days/difference
Response: as above, but for each date 
Structure: Elements as above but in an array
