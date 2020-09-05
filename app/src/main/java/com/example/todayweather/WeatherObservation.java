package com.example.todayweather;

import android.util.Log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class WeatherObservation
{
    // fields
    public static final String TAG = "Weather";

    private LocalDateTime dateTime;
    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private double wind_speed;
    private String description;
    private String country;
    private String city_name;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    // constructor
    public WeatherObservation(long dateTime, double temp, double pressure, double humidity, double temp_min,
                              double temp_max, double wind_speed, String description, String country, String city,
                              long sunriseLong, long sunsetLong)
    {
        // convert unix time to LocalDateTime
        this.dateTime = Instant.ofEpochSecond(dateTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.sunrise = Instant.ofEpochSecond(sunriseLong).atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.sunset = Instant.ofEpochSecond(sunsetLong).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // other fields
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
    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

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

    public LocalDateTime getSunrise()
    {
        return sunrise;
    }

    public LocalDateTime getSunset()
    {
        return sunset;
    }
}
