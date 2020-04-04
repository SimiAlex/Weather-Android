package com.example.todayweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    // data fields
    private static final String API_KEY = "29fe7b237d05764a1bd1308c911e8f74";
    private static List<WeatherObservation> database = new CopyOnWriteArrayList<>();
    private String stringURL;
    private String city;

    // UI fields
    private Button button_update;
    private TextInputLayout city_TIL;
    private TextView temperature_TextView;
    private TextView city_TextView;
    private TextView description_TextView;
    private TextView windSpeed_TextView;
    private TextView humidity_TextView;
    private TextView minMax_TextView;

    // OnCompletedNetworkingListener declaration
    private OnCompletedNetworkingListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize layout
        button_update = findViewById(R.id.button_update);
        city_TIL = findViewById(R.id.city_InputLayout);
        temperature_TextView = findViewById(R.id.textView_temperature);
        city_TextView = findViewById(R.id.city_TextView);
        description_TextView = findViewById(R.id.textView_description);
        windSpeed_TextView = findViewById(R.id.textView_windSpeed);
        humidity_TextView = findViewById(R.id.textView_humidity);
        minMax_TextView = findViewById(R.id.textView_minMax);

        // initialize a listener
        mListener = new OnCompletedNetworkingListener()
        {
            @Override
            public void handleEndOfNetworking()
            {
                updateUI();
            }
        };

        // button clicks
        button_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // generate stringURL for the desired city (includes validation of city name)
                getDataInNewThread();
            }
        });
    }

    public void getDataInNewThread()
    {
        // generate stringURL for the desired city (includes validation of city name)
        city = city_TIL.getEditText().getText().toString().trim();
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
        ExecutorService service = null;

        try
        {
            service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    JSONObject jObj = NetFun.requestInfoFromNetwork(stringURL);
                    WeatherObservation wObs = NetFun.makeJsonToObservation(jObj);
                    if (wObs != null)
                    {
                        database.add(wObs);
                    }
                    mListener.handleEndOfNetworking();
                }
            });
        }
        finally
        {
            if (service != null)
            {
                service.shutdown();
            }
        }
    }

    public void updateUI()
    {
        if (database.size() > 0)
        {
            WeatherObservation currentWO = database.get(database.size()-1);

            city_TextView.setText(currentWO.getCity_name() + " (" + currentWO.getCountry() + ")");
            temperature_TextView.setText(String.format(Locale.UK, "%.0f°C", currentWO.getTemp()));
            description_TextView.setText(currentWO.getDescription());
            windSpeed_TextView.setText(String.valueOf(currentWO.getWind_speed()));
            humidity_TextView.setText(String.format(Locale.UK, "%.0f%%", currentWO.getHumidity()));
            minMax_TextView.setText(String.format(Locale.UK, "%.0f  |  %.0f°C", currentWO.getTemp_min(), currentWO.getTemp_max()));
        }
    }
}
