package com.famgy.presenter.util;

import android.util.Log;

import com.famgy.modle.area.City;
import com.famgy.modle.area.County;
import com.famgy.modle.area.Province;
import com.famgy.modle.weather.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by famgy on 3/7/18.
 */

public class Utility {

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();

            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> handleProvinceResponse(String address, String response) {
        try {
            ArrayList<String> arrayList = new ArrayList();
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0; i < 1; i++) {
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setId(provinceObject.getInt("id"));
                province.setProvinceName(provinceObject.getString("name"));

                if (provinceObject.getString("name").equals("山西")) {
                    Log.e("=======TEST=========", "shanxi");
                }

                province.save();

                arrayList.add(address + "/" + provinceObject.getInt("id"));
            }

            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> handleCityResponse(String address, String response) {
        try {
            ArrayList<String> arrayList = new ArrayList();
            JSONArray allCities = new JSONArray(response);
            for (int i = 0; i < allCities.length(); i++) {
                JSONObject CityObject = allCities.getJSONObject(i);
                City city = new City();
                city.setId(CityObject.getInt("id"));
                city.setCityName(CityObject.getString("name"));

                if (CityObject.getString("name").equals("临汾")) {
                    Log.e("=======TEST=========", "linfen");
                }

                city.save();

                arrayList.add(address + "/" + CityObject.getInt("id"));
            }

            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void handleCountyResponse(String address, String response) {
        try {
            JSONArray allCounty = new JSONArray(response);
            for (int i = 0; i < allCounty.length(); i++) {
                JSONObject countyObject = allCounty.getJSONObject(i);
                County county = new County();
                county.setId(countyObject.getInt("id"));
                county.setWeather_Id(countyObject.getString("weather_id"));
                county.setCountyName(countyObject.getString("name"));
                county.setWeather_url(address);

                if (countyObject.getString("name").equals("隰县")) {
                    Log.e("=======TEST=========", county.getCountyName() + ", " + county.getId() + ", " + county.getWeather_Id());
                }

                county.save();

                //Log.e("=======TEST=========", county.getCountyName() + ", " + county.getId() + ", " + county.getWeather_Id());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
