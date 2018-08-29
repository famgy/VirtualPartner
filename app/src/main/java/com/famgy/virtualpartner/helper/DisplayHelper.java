package com.famgy.virtualpartner.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;

public class DisplayHelper {

    public static String getResolution(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        return String.valueOf(screenWidth + "x" + screenHeight);
    }

    public static String getDPI(Activity context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int densityDPI = (int)(metrics.density * 160f);
        return String.valueOf(densityDPI) + " dpi";
    }

    public static String getScreenSize(Activity context) {
        int widthPixels = 0;
        int heightPixels = 0;
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        try {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        double x = Math.pow(widthPixels / displayMetrics.xdpi, 2);
        double y = Math.pow(heightPixels / displayMetrics.ydpi, 2);
        double screenSize = Math.sqrt(x + y);
        return String.format(Locale.ENGLISH, "%.2f", screenSize) + "\"";
    }

    public static String getRefreshValue(Activity context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshValue = display.getRefreshRate();
        return String.format(Locale.ENGLISH, "%.2f", refreshValue) + "Hz";
    }

    public static String getCurrentBrightness() {
        int currentBrighness = 0;
        int maxBrighness = 0;
        try {
            RandomAccessFile randomAccessFileCurrent = new RandomAccessFile("/sys/class/leds/lcd-backlight/brightness", "r");
            boolean doneCurrent = false;
            while (!doneCurrent) {
                String line = randomAccessFileCurrent.readLine();
                if (null == line) {
                    doneCurrent = true;
                    break;
                }
                currentBrighness = Integer.parseInt(line);
            }
            RandomAccessFile randomAccessFileMax = new RandomAccessFile("/sys/class/leds/lcd-backlight/max_brightness", "r");
            boolean doneMax = false;
            while (!doneMax) {
                String line = randomAccessFileMax.readLine();
                if (null == line) {
                    doneMax = true;
                    break;
                }
                maxBrighness = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf((currentBrighness*100)/maxBrighness) + "% (" + currentBrighness + " / " + maxBrighness +")";
    }


}
