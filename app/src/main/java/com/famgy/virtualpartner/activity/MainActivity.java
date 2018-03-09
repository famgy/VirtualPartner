package com.famgy.virtualpartner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.famgy.virtualpartner.R;

public class MainActivity extends BaseActivity {

    private TextView tv;
    private Button btn_weather_login;
    private Button btn_fanye_example;
    private Button btn_weather_info;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btn_weather_login = (Button) findViewById(R.id.btn_weather_login);
        btn_fanye_example = (Button) findViewById(R.id.btn_fanye_example);
        btn_weather_info = (Button) findViewById(R.id.btn_weather_info);


        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    public void toWeather(View view) {
        //btn_weather_login.setEnabled(false);

        Intent intent = new Intent(context, WeatherActivity.class);
        startActivity(intent);
    }

    public void toFanye(View view) {

        Intent intent = new Intent(context, FanyeActivity.class);
        startActivity(intent);
    }

    public void toWeatherInfo(View view) {
        //btn_weather_login.setEnabled(false);
        Intent intent = new Intent(context, WeatherPagesActivity.class);
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
