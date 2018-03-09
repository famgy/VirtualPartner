package com.famgy.virtualpartner.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.famgy.virtualpartner.R;

import java.util.ArrayList;

public class FanyeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanye);

        ViewPager layout_view_page = (ViewPager) findViewById(R.id.layout_view_page_fanye);

        PagerAdapter pagerAdapter = new MyPagerAdapter(this);

        layout_view_page.setAdapter(pagerAdapter);
    }

    private class MyPagerAdapter extends PagerAdapter{

        private Context context;
        private ArrayList<View> views;

        public MyPagerAdapter(Context context) {
            this.context = context;

            views = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                TextView tv = new TextView(context);
                tv.setText("" + i);
                views.add(tv);
            }

            View view = View.inflate(context, R.layout.activity_weather, null);
            views.add(view);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public View instantiateItem(ViewGroup container, int position){
            container.addView(views.get(position));

            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
