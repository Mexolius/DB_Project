# DB_Project

This is a project for Databases laboratories.

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
