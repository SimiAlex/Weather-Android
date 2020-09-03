package com.example.todayweather;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public final class NetFun
{
    public static JSONObject requestInfoFromNetwork(String stringURL)
    {
        JSONObject jObj = null;
        HttpURLConnection huc = null;

        try
        {
            huc = (HttpURLConnection) (new URL(stringURL)).openConnection();
            huc.setRequestMethod("GET");
            huc.setReadTimeout(10_000);
            huc.setConnectTimeout(10_000);
            huc.connect();

            if (huc.getResponseCode() == 200)
            {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream())))
                {
                    StringBuilder sb = new StringBuilder();
                    String s = null;

                    while ((s = br.readLine()) != null)
                    {
                        sb.append(s);
                    }

                    jObj = new JSONObject(sb.toString());
                }
            }
            else
            {
                return null;
            }
        }
        catch (JSONException | IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (huc != null)
            {
                huc.disconnect();
            }
        }

        return jObj;
    }


    public static WeatherObservation makeJsonToObservation(JSONObject jObj)
    {
        if (jObj == null)
        {
            return null;
        }

        double temp, pressure, humidity, temp_min, temp_max, wind_speed;
        String description, country, city_name;
        WeatherObservation obs = null;

        try
        {
            temp = jObj.getJSONObject("main").getDouble("temp");
            pressure = jObj.getJSONObject("main").getDouble("pressure");
            humidity = jObj.getJSONObject("main").getDouble("humidity");
            temp_min = jObj.getJSONObject("main").getDouble("temp_min");
            temp_max = jObj.getJSONObject("main").getDouble("temp_max");
            wind_speed = jObj.getJSONObject("wind").getDouble("speed");
            description = jObj.getJSONArray("weather").getJSONObject(0).getString("description");
            country = jObj.getJSONObject("sys").getString("country");
            city_name = jObj.getString("name");

            obs = new WeatherObservation(temp, pressure, humidity, temp_min, temp_max, wind_speed, description, country, city_name);
        }
        catch (JSONException e)
        {
            Log.d("Today Weather", "JSONException thrown in makeJsonToObservation()");
        }

        return obs;
    }
}
