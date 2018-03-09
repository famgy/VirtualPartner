package com.famgy.virtualpartner.util;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by famgy on 3/9/18.
 */

public class VpPagerAdapter extends PagerAdapter {

    private ArrayList<View> views;

    public VpPagerAdapter(ArrayList<View> pages) {
        views = pages;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position){
        container.addView(views.get(position));

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
