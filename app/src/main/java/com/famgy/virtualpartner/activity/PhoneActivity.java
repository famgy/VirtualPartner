package com.famgy.virtualpartner.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.fragment.AndroidFragment;
import com.famgy.virtualpartner.fragment.BatteryFragment;
import com.famgy.virtualpartner.fragment.DeviceFragment;
import com.famgy.virtualpartner.fragment.DisplayFragment;
import com.famgy.virtualpartner.fragment.RootFragment;
import com.famgy.virtualpartner.fragment.SensorFragment;
import com.famgy.virtualpartner.fragment.SoCFragment;
import com.famgy.virtualpartner.fragment.TelephonyFragment;
import com.famgy.virtualpartner.util.ViewPagerAdapter;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;


public class PhoneActivity extends BaseActivity {

    private TextView tv_network;
    private Toolbar toolbar;
    private String[] stringTitleToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] intTabIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        stringTitleToolbar = new String[] { "Android", getString(R.string.SoC), getString(R.string.Device), getString(R.string.Display), getString(R.string.Battery), getString(R.string.Telephony), getString(R.string.Sensor), getString(R.string.Root) };

        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                toolbar.setTitle(stringTitleToolbar[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        intTabIcons = new int[] {
                R.drawable.ic_android_white_24dp,
                R.drawable.ic_memory_white_24dp,
                R.drawable.ic_smartphone_white_24dp,
                R.drawable.ic_display_white_24dp,
                R.drawable.ic_battery_charging_full_white_24dp,
                R.drawable.ic_sim_card_white_24dp,
                R.drawable.ic_screen_rotation_white_24dp,
                R.drawable.ic_root_white_24dp};
        setupTabIcons(tabLayout);

        //tv_network = (TextView) findViewById(R.id.text_network);
        //getNetInfo();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AndroidFragment(), "Android");
        viewPagerAdapter.addFragment(new SoCFragment(), "SoC");
        viewPagerAdapter.addFragment(new DeviceFragment(), "Device");
        viewPagerAdapter.addFragment(new DisplayFragment(), "Display");
        viewPagerAdapter.addFragment(new BatteryFragment(), "Battery");
        viewPagerAdapter.addFragment(new TelephonyFragment(), "Telephony");
        viewPagerAdapter.addFragment(new SensorFragment(), "Sensor");
        viewPagerAdapter.addFragment(new RootFragment(), "Root");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(intTabIcons[0]);
        tabLayout.getTabAt(1).setIcon(intTabIcons[1]);
        tabLayout.getTabAt(2).setIcon(intTabIcons[2]);
        tabLayout.getTabAt(3).setIcon(intTabIcons[3]);
        tabLayout.getTabAt(4).setIcon(intTabIcons[4]);
        tabLayout.getTabAt(5).setIcon(intTabIcons[5]);
        tabLayout.getTabAt(6).setIcon(intTabIcons[6]);
        tabLayout.getTabAt(7).setIcon(intTabIcons[7]);
    }

    private void getNetInfo() {
        String netInfo = "";
        StringBuilder builder = new StringBuilder();

        try {
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface networkInterface : networkInterfaceList) {
                String displayName = networkInterface.getDisplayName() + " " + networkInterface.getInterfaceAddresses().toString();
                builder.append(displayName + "\n");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        netInfo = builder.toString();

        tv_network.setText(netInfo);
    }
}
