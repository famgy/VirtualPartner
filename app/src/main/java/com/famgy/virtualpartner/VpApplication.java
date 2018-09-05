package com.famgy.virtualpartner;

import android.app.Application;

import com.famgy.presenter.ActionCallbackListener;
import com.famgy.presenter.AppAction;
import com.famgy.presenter.AppActionImpl;

import org.litepal.LitePal;

import java.util.ArrayList;


/**
 * Created by famgy on 3/6/18.
 */

public class VpApplication extends Application{

    private AppAction appAction;
    final String addressChina = "http://guolin.tech/api/china";

    @Override
    public void onCreate() {
        super.onCreate();

//        LitePal.initialize(this);
//        appAction = new AppActionImpl(this);
//        appAction.getAreaInfo(addressChina, "province", new ActionCallbackListener<ArrayList<String>>() {
//            @Override
//            public void onSuccess(ArrayList<String> data) {
//                for (String addressProvince : data) {
//                    appAction.getAreaInfo(addressProvince, "city", new ActionCallbackListener<ArrayList<String>>() {
//                        @Override
//                        public void onSuccess(ArrayList<String> data) {
//                            for (String addressCity : data) {
//                                appAction.getAreaInfo(addressCity, "county", null);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(String errorEvent, String message) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String errorEvent, String message) {
//
//            }
//        });
    }

    public AppAction getAppAction() {
        return appAction;
    }
}
