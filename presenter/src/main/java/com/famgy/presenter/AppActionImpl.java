package com.famgy.presenter;

import android.widget.Toast;

import com.famgy.model.Weather;
import com.famgy.presenter.util.HttpUtil;
import com.famgy.presenter.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by famgy on 3/6/18.
 */

public class AppActionImpl implements AppAction {

    private Weather weather;

    @Override
    public void showWeacherInfo(Weather weather) {
        String place = weather.getPlace();
        String status = weather.getStatus();
    }

    @Override
    public void requestWeatherInfo(final ActionCallbackListener<Weather> listener) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=CN101010100&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailure(ErrorEvent.ACTION_FAILED, "请求url失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                weather = Utility.handleWeatherResponse(responseText);
                listener.onSuccess(weather);
            }
        });
    }
}
