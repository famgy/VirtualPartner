package com.famgy.virtualpartner.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.helper.DisplayHelper;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private GLSurfaceView glSurfaceView;
    private final String FONT = "FONT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Typeface typeface;

        sharedPreferences = getSharedPreferences("VirtualPartner", MODE_PRIVATE);
        if (sharedPreferences.getString(FONT, "Roboto").equals("Google Sans")) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/" + "GoogleSans" + ".ttf");
        } else {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/" + sharedPreferences.getString(FONT, "Roboto") + ".ttf");
        }

        TextView textViewWelcomeTo = findViewById(R.id.textViewWelcomeToSplash);
        TextView textViewVirtualPartnerToSplash = findViewById(R.id.textViewVirtualPartnerToSplash);

        textViewWelcomeTo.setTypeface(typeface);
        textViewVirtualPartnerToSplash.setTypeface(typeface);

        sharedPreferences
                .edit()
                .putString("SCREEN_INCHES", DisplayHelper.getScreenSize(SplashActivity.this))
                .putString("RESOLUTION", DisplayHelper.getResolution(SplashActivity.this))
                .apply();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
