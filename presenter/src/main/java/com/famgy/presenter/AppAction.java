package com.famgy.presenter;

import com.famgy.model.Weather;

/**
 * Created by famgy on 3/6/18.
 */

public interface AppAction {

    public void requestWeatherInfo(final ActionCallbackListener<Weather> listener);

    public void requestBingPic(final ActionCallbackListener<String> listener);

    public Weather getWeatherInfo(final String weatherInfo);
}
