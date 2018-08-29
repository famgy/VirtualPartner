package com.famgy.virtualpartner.fragment;

/**
 * Created by famgy on 6/4/18.
 */


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.famgy.virtualpartner.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RootFragment extends Fragment {

    private Activity activity;

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_root, container, false);

        //SharedPreferences sharedPreferences = activity.getSharedPreferences("VirtualPartner", Context.MODE_PRIVATE);
        TextView textView = layoutView.findViewById(R.id.textView_root);

        if (checkRootState() == true) {
            textView.setText("It is root");
        } else {
            textView.setText("It is not root");
        }

        return layoutView;
    }

    public static boolean checkRootState() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        String sBinPath = "/su/bin/su";
        if (new File(binPath).exists() && isExecutable(binPath)) {
            Log.e("gxb", "手机root的状态--->" + true);
            return true;
        }

        if (new File(xBinPath).exists() && isExecutable(xBinPath)) {
            Log.e("gxb", "手机root的状态--->" + true);
            return true;
        }

        if (new File(sBinPath).exists() && isExecutable(sBinPath)) {
            Log.e("gxb", "手机root的状态--->" + true);
            return true;
        }

        Log.e("gxb", "手机root的状态--->" + false);
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }
}