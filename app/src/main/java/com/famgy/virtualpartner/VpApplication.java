package com.famgy.virtualpartner;

import android.app.Application;

import com.famgy.presenter.AppAction;
import com.famgy.presenter.AppActionImpl;


/**
 * Created by famgy on 3/6/18.
 */

public class VpApplication extends Application{

    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();

        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}
