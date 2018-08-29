package com.famgy.virtualpartner.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.famgy.modle.area.County;
import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.object.WeatherObject;
import com.famgy.virtualpartner.util.VpPagerAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class WeatherPagesActivity extends BaseActivity {

    private ArrayList<View> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather_pages);

        initViewPages();
    }

    private void initViewPages() {
        ViewPager layout_view_page = (ViewPager) findViewById(R.id.layout_view_page);
        pages = getPages();
        if (pages == null) {
            Log.e("VTRTUAL_PARTNER", "Not find weather pages!");
            return;
        }
        PagerAdapter pagerAdapter = new VpPagerAdapter(pages);
        layout_view_page.setAdapter(pagerAdapter);
    }

    //http://guolin.tech/api/weather?cityid=CN101010100&key=bc0418b57b2d4918819d3974ac1285d9
    private ArrayList<View> getPages () {
        ArrayList<View> views = new ArrayList<>();
        ArrayList<County> countyArrayList = (ArrayList<County>) DataSupport.where("countyName = ?", "临汾").find(County.class);
        if (countyArrayList.size() <= 0) {
            Log.e("VTRTUAL_PARTNER", "Not find county!");
            return null;
        }
        County county = countyArrayList.get(0);

        final String weatherUrl = "http://guolin.tech/api/weather?cityid=" + county.getWeather_Id() + "&key=bc0418b57b2d4918819d3974ac1285d9";
        Log.e("====WEATHER_URL====", weatherUrl);
        WeatherObject weatherObject= new WeatherObject(context, this, appAction, weatherUrl);
        views.add(weatherObject.view);


        ArrayList<County> countyArrayList2 = (ArrayList<County>) DataSupport.where("countyName = ?", "北京").find(County.class);
        if (countyArrayList2.size() <= 0) {
            Log.e("VTRTUAL_PARTNER", "Not find county!");
            return null;
        }
        County county2 = countyArrayList2.get(0);

        final String weatherUrl2 = "http://guolin.tech/api/weather?cityid=" + county2.getWeather_Id() + "&key=bc0418b57b2d4918819d3974ac1285d9";
        Log.e("====WEATHER_URL====", weatherUrl2);


        WeatherObject weatherObject2= new WeatherObject(context, this, appAction, weatherUrl2);
        views.add(weatherObject2.view);

        return views;
    }
}
