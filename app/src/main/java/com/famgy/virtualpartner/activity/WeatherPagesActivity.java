package com.famgy.virtualpartner.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.object.WeatherObject;
import com.famgy.virtualpartner.util.VpPagerAdapter;

import java.util.ArrayList;

public class WeatherPagesActivity extends BaseActivity {

    private ArrayList<View> pages;

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
        setContentView(R.layout.activity_weather_pages);

        initViewPages();
    }

    private void initViewPages() {
        ViewPager layout_view_page = (ViewPager) findViewById(R.id.layout_view_page);
        pages = getPages();
        PagerAdapter pagerAdapter = new VpPagerAdapter(pages);
        layout_view_page.setAdapter(pagerAdapter);
    }

    private ArrayList<View> getPages () {
        ArrayList<View> views = new ArrayList<>();

        WeatherObject weatherObject= new WeatherObject(context, this, appAction);
        views.add(weatherObject.view);

        WeatherObject weatherObject2= new WeatherObject(context, this, appAction);
        views.add(weatherObject2.view);

        return views;
    }
}
