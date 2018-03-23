package com.famgy.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.famgy.modle.weather.Weather;
import com.famgy.presenter.util.HttpUtil;
import com.famgy.presenter.util.Utility;

import java.io.IOException;
import java.util.ArrayList;

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

        Log.e("=====BINGPIC-send=====", requestBingPic);
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

                Log.e("====BINGPIC-result====", bingPic);

                listener.onSuccess(bingPic);
            }
        });
    }

    @Override
    public void requestWeatherInfo(final String weatherUrl, final ActionCallbackListener<Weather> listener) {
        Log.e("====WEATHER-send====", weatherUrl);
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

    @Override
    public void getAreaInfo(final String address, final String type, final ActionCallbackListener<ArrayList<String>> listener) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailure(ErrorEvent.ACTION_FAILED, "请求url失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                if ("province".equals(type)) {
                    ArrayList<String> arrayListProvince = Utility.handleProvinceResponse(address, responseText);
                    if (arrayListProvince != null) {
                        listener.onSuccess(arrayListProvince);
                    }
                } else if ("city".equals(type)) {
                    ArrayList<String> arrayListCity = Utility.handleCityResponse(address, responseText);
                    if (arrayListCity != null) {
                        listener.onSuccess(arrayListCity);
                    }
                } else if ("county".equals(type)){
                    Utility.handleCountyResponse(address, responseText);
                }
            }
        });
    }
}
