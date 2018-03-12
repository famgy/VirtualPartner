package com.famgy.presenter;

import com.famgy.modle.weather.Weather;

import java.util.ArrayList;

/**
 * Created by famgy on 3/6/18.
 */

public interface AppAction {

    public void requestWeatherInfo(final String weatherUrl, final ActionCallbackListener<Weather> listener);

    public void requestBingPic(final ActionCallbackListener<String> listener);

    public Weather getWeatherInfo(final String weatherInfo);

    public void getAreaInfo(final String address, final String type, final ActionCallbackListener<ArrayList<String>> listener);
}
