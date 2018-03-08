package com.famgy.virtualpartner.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.famgy.model.Forecast;
import com.famgy.model.Weather;
import com.famgy.presenter.ActionCallbackListener;
import com.famgy.virtualpartner.R;

import org.w3c.dom.Text;

public class WeatherActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout forecastLayout;
    private ImageView img_weather_background;
    private SharedPreferences prefs;
    private TextView text_title_city;
    private TextView text_now_degree;
    private TextView text_now_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);

        initViews();
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        img_weather_background = (ImageView) findViewById(R.id.img_weather_background);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);

        text_title_city = (TextView) findViewById(R.id.text_title_city);
        text_now_degree = (TextView) findViewById(R.id.text_now_degree);
        text_now_info = (TextView) findViewById(R.id.text_now_info);

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(context).load(bingPic).into(img_weather_background);
        } else {
            loadBingPic();
        }

        final String weatherInfo = prefs.getString("weather_info", null);
        if (weatherInfo != null) {
            loadWeatherInfo(weatherInfo);
        } else {
            updateWeatherInfo();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeatherInfo();
            }
        });
    }

    private void loadBingPic() {
        appAction.requestBingPic(new ActionCallbackListener<String>() {
            @Override
            public void onSuccess(final String bingPic) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(context).load(bingPic).into(img_weather_background);
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {

            }
        });
    }

    private void loadWeatherInfo(String weatherInfo) {
        final Weather weather = appAction.getWeatherInfo(weatherInfo);
        if (weather != null) {
            showWeatherInfo(weather);
        }
    }

    //http://guolin.tech/api/weather?cityid=CN101010100&key=bc0418b57b2d4918819d3974ac1285d9
    private void updateWeatherInfo() {
        appAction.requestWeatherInfo(new ActionCallbackListener<Weather>() {
            @Override
            public void onSuccess(final Weather weather) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeatherInfo(weather);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        text_title_city.setText(weather.basic.cityName);

        text_now_degree.setText(weather.now.temperature + "℃");
        text_now_info.setText(weather.now.more.info);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(context).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView text_forecast_date = (TextView) view.findViewById(R.id.text_forecast_date);
            TextView text_forecast_info = (TextView) view.findViewById(R.id.text_forecast_info);
            TextView text_forecast_max = (TextView) view.findViewById(R.id.text_forecast_max);
            TextView text_forecast_min = (TextView) view.findViewById(R.id.text_forecast_min);

            text_forecast_date.setText(forecast.date);
            text_forecast_info.setText(forecast.more.info);
            text_forecast_max.setText(forecast.temperature.max);
            text_forecast_min.setText(forecast.temperature.min);

            forecastLayout.addView(view);
        }
    }
}
