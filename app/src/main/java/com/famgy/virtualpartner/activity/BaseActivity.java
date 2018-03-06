package com.famgy.virtualpartner.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.famgy.presenter.AppAction;
import com.famgy.virtualpartner.VpApplication;

/**
 * Created by famgy on 3/6/18.
 */

public class BaseActivity extends AppCompatActivity {

    public Context context;
    public VpApplication application;
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

        context = getApplicationContext();
        application = (VpApplication)this.getApplication();
        appAction = application.getAppAction();
    }
}
