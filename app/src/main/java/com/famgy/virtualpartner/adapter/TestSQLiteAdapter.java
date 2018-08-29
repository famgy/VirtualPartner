package com.famgy.virtualpartner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.famgy.modle.call.CallHistory;
import com.famgy.modle.user.UserInfo;
import com.famgy.virtualpartner.R;

import java.util.List;

/**
 * Created by famgy on 6/11/18.
 */

public class TestSQLiteAdapter extends ArrayAdapter<UserInfo>{

    private int resourceId;

    public TestSQLiteAdapter(@NonNull Context context, int resource, List<UserInfo> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        UserInfo userInfo = getItem(position);

        TextView tv_user_name = (TextView) view.findViewById(R.id.user_name);
        TextView tv_user_age = (TextView) view.findViewById(R.id.user_age);

        tv_user_name.setText(userInfo.getName());
        tv_user_age.setText(String.valueOf(userInfo.getAge()));

        return view;
    }
}
