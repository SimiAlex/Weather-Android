package com.example.todayweather;

import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MyListener implements OnCompletedNetworkingListener
{
    // fields
    private List<WeatherObservation> database;
    private TextView temperature_TV;
    private TextView city_TV;
    private TextView description_TV;
    private TextView windSpeed_TV;
    private TextView humidity_TV;
    private TextView minMax_TV;

    // constructor
    public MyListener(List<WeatherObservation> database, TextView temperature_TV, TextView city_TV, TextView description_TV, TextView windSpeed_TV, TextView humidity_TV, TextView minMax_TV)
    {
        this.temperature_TV = temperature_TV;
        this.city_TV = city_TV;
        this.description_TV = description_TV;
        this.windSpeed_TV = windSpeed_TV;
        this.humidity_TV = humidity_TV;
        this.minMax_TV = minMax_TV;
        this.database = database;
    }

    @Override
    public void handleEndOfNetworking()
    {
        // some action to take: update UI on UI thread (????)
        if (database.size() > 0 && database.get(database.size() - 1) != null)
        {
            WeatherObservation currentWO = database.get(database.size()-1);

            city_TV.setText(currentWO.getCity_name() + " (" + currentWO.getCountry() + ")");
            temperature_TV.setText(String.format(Locale.UK, "%.0f°C", currentWO.getTemp()));
            description_TV.setText(currentWO.getDescription());
            windSpeed_TV.setText(String.valueOf(currentWO.getWind_speed()));
            humidity_TV.setText(String.format(Locale.UK, "%.0f%%", currentWO.getHumidity()));
            minMax_TV.setText(String.format(Locale.UK, "%.0f  |  %.0f°C", currentWO.getTemp_min(), currentWO.getTemp_max()));
        }
    }
}
