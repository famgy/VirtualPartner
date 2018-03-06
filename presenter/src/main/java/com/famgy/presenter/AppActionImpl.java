package com.famgy.presenter;

import com.famgy.model.Weather;

/**
 * Created by famgy on 3/6/18.
 */

public class AppActionImpl implements AppAction {

    @Override
    public void showWeacherInfo(Weather weather) {
        String place = weather.getPlace();
        String status = weather.getStatus();
    }
}
