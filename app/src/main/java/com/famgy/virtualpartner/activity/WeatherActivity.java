package com.famgy.virtualpartner.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.famgy.model.Forecast;
import com.famgy.model.Weather;
import com.famgy.presenter.ActionCallbackListener;
import com.famgy.virtualpartner.R;

public class WeatherActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_weather);

        initViews();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeatherInfo();
            }
        });
    }

    //http://guolin.tech/api/weather?cityid=CN101010100&key=bc0418b57b2d4918819d3974ac1285d9
    private void updateWeatherInfo() {
        Toast.makeText(context, "更新天气", Toast.LENGTH_SHORT).show();

        appAction.requestWeatherInfo(new ActionCallbackListener<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                showWeatherInfo(weather);
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    private void showWeatherInfo(Weather weather) {

        for (Forecast forecast : weather.forecastList) {

        }
    }
}
