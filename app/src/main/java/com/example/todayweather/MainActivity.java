package com.example.todayweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    // data fields
    private static final String API_KEY = "29fe7b237d05764a1bd1308c911e8f74"; // place here your individual API key
    private String stringURL;

    // UI fields
    private Button button_update;
    private TextInputLayout city_TIL;
    private TextView temperature_TextView;
    private TextView city_TextView;
    private TextView description_TextView;
    private TextView windSpeed_TextView;
    private TextView humidity_TextView;
    private TextView minMax_TextView;
    private TextView dateTime_TextView;
    private TextView sunrise_TextView;
    private TextView sunset_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        // initialize layout
        initializeLayoutElements();

        // button clicks
        button_update.setOnClickListener(v -> getDataInNewThread());
    }

    public void getDataInNewThread()
    {
        // generate stringURL for the desired city (includes validation of city name)
        String city = city_TIL.getEditText().getText().toString().trim();
        if (city.isEmpty())
        {
            city_TIL.setError("Field can't be empty");
            return;
        }
        else
        {
            city_TIL.setError(null);
        }
        stringURL = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, API_KEY);

        // thread code
        new Thread(() ->
        {
            JSONObject jObj = NetFun.requestInfoFromNetwork(stringURL);
            WeatherObservation wObs = NetFun.makeJsonToObservation(jObj);

            // update UI on the main thread
            runOnUiThread(() -> updateUI(wObs));

        }).start();
    }

    private void updateUI(WeatherObservation weatherObservation)
    {
        if (weatherObservation != null)
        {
            city_TextView.setText(String.format("%s (%s)", weatherObservation.getCity_name(), weatherObservation.getCountry()));
            temperature_TextView.setText(String.format(Locale.UK, "%.0f°C", weatherObservation.getTemp()));
            description_TextView.setText(weatherObservation.getDescription());
            windSpeed_TextView.setText(String.valueOf(weatherObservation.getWind_speed()));
            humidity_TextView.setText(String.format(Locale.UK, "%.0f%%", weatherObservation.getHumidity()));
            minMax_TextView.setText(String.format(Locale.UK, "%.0f  |  %.0f°C", weatherObservation.getTemp_min(), weatherObservation.getTemp_max()));

            String dateTimeFormatted = String.format("%s\n%s", weatherObservation.getDateTime().toLocalDate().toString(), weatherObservation.getDateTime().toLocalTime().toString());
            dateTime_TextView.setText(dateTimeFormatted);

            // sunrise & sunset
            sunrise_TextView.setText(weatherObservation.getSunrise().toLocalTime().toString());
            sunset_TextView.setText(weatherObservation.getSunset().toLocalTime().toString());
        }
    }

    private void initializeLayoutElements()
    {
        button_update = findViewById(R.id.button_update);
        city_TIL = findViewById(R.id.city_InputLayout);
        temperature_TextView = findViewById(R.id.textView_temperature);
        city_TextView = findViewById(R.id.city_TextView);
        description_TextView = findViewById(R.id.textView_description);
        windSpeed_TextView = findViewById(R.id.textView_windSpeed);
        humidity_TextView = findViewById(R.id.textView_humidity);
        minMax_TextView = findViewById(R.id.textView_minMax);
        dateTime_TextView = findViewById(R.id.dateTime_TextView);
        sunrise_TextView = findViewById(R.id.textView_sunrise);
        sunset_TextView = findViewById(R.id.textView_sunset);
    }
}
