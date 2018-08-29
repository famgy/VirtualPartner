package com.famgy.virtualpartner.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.famgy.modle.call.CallHistory;
import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.adapter.CallHistoryAdapter;
import com.famgy.virtualpartner.util.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CallHistoryActivity extends AppCompatActivity {

    private List<CallHistory> callHistoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        initCallHistory();
        CallHistoryAdapter callHistoryAdapter = new CallHistoryAdapter(CallHistoryActivity.this, R.layout.call_history_item, callHistoriesList);

        ListView listView = (ListView) findViewById(R.id.list_call_history);
        listView.setAdapter(callHistoryAdapter);
    }

    private void initCallHistory() {
        callHistoriesList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        ContentResolver contentResolver = getContentResolver();

        Uri rui = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.NUMBER, // 号码
                CallLog.Calls.DATE,   // 日期
                CallLog.Calls.TYPE    // 类型：来电、去电、未接
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            AskPermission.get_permission(this, Manifest.permission.READ_CALL_LOG);
        }
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            long date = cursor.getLong(1);
            int type = cursor.getInt(2);

            CallHistory callHistory = new CallHistory();
            callHistory.call_number = number;
            callHistory.call_date = simpleDateFormat.format(date);

            if (type == CallLog.Calls.INCOMING_TYPE) {
                callHistory.call_type = "呼入";
            } else if (type == CallLog.Calls.OUTGOING_TYPE) {
                callHistory.call_type = "呼出";
            } else if (type == CallLog.Calls.MISSED_TYPE) {
                callHistory.call_type = "未接";
            } else {
                callHistory.call_type = "未知";
            }

            callHistoriesList.add(callHistory);
        }
        cursor.close();
    }
}
