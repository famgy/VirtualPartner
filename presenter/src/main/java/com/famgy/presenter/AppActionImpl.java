package com.famgy.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    private Context context;

    public AppActionImpl(Context context) {
        this.context = context;
    }

    @Override
    public void requestBingPic(final ActionCallbackListener<String> listener) {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailure(ErrorEvent.ACTION_FAILED, "请求url失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();

                listener.onSuccess(bingPic);
            }
        });
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

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("weather_info", responseText);
                editor.apply();

                Weather weather = Utility.handleWeatherResponse(responseText);
                listener.onSuccess(weather);
            }
        });
    }

    @Override
    public Weather getWeatherInfo(final String weatherInfo) {
        return Utility.handleWeatherResponse(weatherInfo);
    }
}
