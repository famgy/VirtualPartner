package com.famgy.virtualpartner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.famgy.virtualpartner.R;

public class MainActivity extends BaseActivity {

    private TextView tv;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(MainActivity.this, "main-onCreate", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    public void toWeatherInfo(View view) {
        //btn_weather_login.setEnabled(false);
        Intent intent = new Intent(context, WeatherPagesActivity.class);
        startActivity(intent);
    }

    public void toFanye(View view) {

        Intent intent = new Intent(context, FanyeActivity.class);
        startActivity(intent);
        //finish();
    }

    public void toPhoneInfo(View view) {

        Intent intent = new Intent(context, PhoneActivity.class);
        startActivity(intent);
        //finish();
    }

    public void toVpnManager(View view) {

        Intent intent = new Intent(context, VpnActivity.class);
        startActivity(intent);
        //finish();
    }

    public void toCallHistory(View view) {

        Intent intent = new Intent(context, CallHistoryActivity.class);
        startActivity(intent);
        //finish();
    }

    public void toSQLiteTest(View view) {
        Intent intent = new Intent(context, SQLiteTestActivity.class);
        startActivity(intent);
    }

    public void toLineDecript(View view) {
        Intent intent = new Intent(context, LineDecriptActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(MainActivity.this, "main-onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(MainActivity.this, "main-onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(MainActivity.this, "main-onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(MainActivity.this, "main-onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(MainActivity.this, "main-onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(MainActivity.this, "main-onDestroy", Toast.LENGTH_SHORT).show();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
