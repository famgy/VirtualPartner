package com.famgy.virtualpartner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.famgy.modle.call.CallHistory;
import com.famgy.virtualpartner.R;

import java.util.List;

/**
 * Created by famgy on 6/11/18.
 */

public class CallHistoryAdapter extends ArrayAdapter<CallHistory>{

    private int resourceId;

    public CallHistoryAdapter(@NonNull Context context, int resource, List<CallHistory> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        CallHistory callHistory = getItem(position);

        TextView tv_call_number = (TextView) view.findViewById(R.id.call_number);
        TextView tv_call_date = (TextView) view.findViewById(R.id.call_date);
        TextView tv_call_type = (TextView) view.findViewById(R.id.call_type);

        tv_call_number.setText(callHistory.call_number);
        tv_call_type.setText(callHistory.call_type);
        tv_call_date.setText(callHistory.call_date);

        return view;
    }
}
