package com.example.todayweather;

public final class WeatherObservation
{
    // fields
    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double wind_speed;
    private String description;
    private String country;
    private String city_name;

    // constructor
    public WeatherObservation(double temp, double pressure, double humidity, double temp_min, double temp_max, double wind_speed, String description, String country, String city)
    {
        this.temp = temp - 273.15;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min - 273.15;
        this.temp_max = temp_max - 273.15;
        this.wind_speed = wind_speed;
        this.description = description;
        this.country = country;
        this.city_name = city;
    }

    // methods
    public double getTemp()
    {
        return temp;
    }

    public double getPressure()
    {
        return pressure;
    }

    public double getHumidity()
    {
        return humidity;
    }

    public double getTemp_min()
    {
        return temp_min;
    }

    public double getTemp_max()
    {
        return temp_max;
    }

    public double getWind_speed()
    {
        return wind_speed;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCountry()
    {
        return country;
    }

    public String getCity_name()
    {
        return city_name;
    }
}
