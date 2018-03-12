package com.famgy.virtualpartner.object;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.famgy.modle.weather.Forecast;
import com.famgy.modle.weather.Weather;
import com.famgy.presenter.ActionCallbackListener;
import com.famgy.presenter.AppAction;
import com.famgy.virtualpartner.R;

/**
 * Created by famgy on 3/9/18.
 */

public class WeatherObject {

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView img_weather_background;
    LinearLayout forecastLayout;
    TextView text_title_city;
    TextView text_now_degree;
    TextView text_now_info;
    TextView text_aqi_value;
    TextView text_api_pm25;
    TextView text_suggestion_comfort;
    TextView text_suggestion_car_wash;
    TextView text_suggestion_sport;
    public View view;
    Context context;
    Activity activity;
    String address;
    AppAction appAction;

    public WeatherObject(Context context, Activity activity, AppAction appAction, String address) {
        this.context = context;
        this.activity = activity;
        this.appAction = appAction;
        this.address = address;
        view = View.inflate(context, R.layout.activity_weather, null);

        initView();
    }

    public void initView()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        img_weather_background = (ImageView) view.findViewById(R.id.img_weather_background);
        forecastLayout = (LinearLayout) view.findViewById(R.id.forecast_layout);

        text_title_city = (TextView) view.findViewById(R.id.text_title_city);
        text_now_degree = (TextView) view.findViewById(R.id.text_now_degree);
        text_now_info = (TextView) view.findViewById(R.id.text_now_info);
        text_aqi_value = (TextView) view.findViewById(R.id.text_aqi_value);
        text_api_pm25 = (TextView) view.findViewById(R.id.text_api_pm25);
        text_suggestion_comfort = (TextView) view.findViewById(R.id.text_suggestion_comfort);
        text_suggestion_car_wash = (TextView) view.findViewById(R.id.text_suggestion_car_wash);
        text_suggestion_sport = (TextView) view.findViewById(R.id.text_suggestion_sport);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
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
                activity.runOnUiThread(new Runnable() {
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
        appAction.requestWeatherInfo(address, new ActionCallbackListener<Weather>() {
            @Override
            public void onSuccess(final Weather weather) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeatherInfo(weather);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                activity.runOnUiThread(new Runnable() {
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

        text_aqi_value.setText(weather.aqi.city.aqi);
        text_api_pm25.setText(weather.aqi.city.pm25);

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        text_suggestion_comfort.setText(comfort);
        text_suggestion_car_wash.setText(carWash);
        text_suggestion_sport.setText(sport);
    }
}
