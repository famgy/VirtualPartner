package com.famgy.virtualpartner.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.famgy.modle.user.UserInfo;
import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.adapter.TestSQLiteAdapter;
import com.famgy.virtualpartner.db.TestSQLiteDB;

import java.util.ArrayList;

public class SQLiteTestActivity extends AppCompatActivity {

    TestSQLiteDB testSQLiteDB;
    TextView tv_name;
    TextView tv_age;
    TestSQLiteAdapter testSQLiteAdapter;
    ArrayList<UserInfo> userInfoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        intView();


        userInfoArrayList = testSQLiteDB.getUserInfo();
        testSQLiteAdapter = new TestSQLiteAdapter(this, R.layout.test_sqlite_user_item, userInfoArrayList);

        ListView listView = (ListView) findViewById(R.id.list_test_sqlite);
        listView.setAdapter(testSQLiteAdapter);

    }

    private void intView() {
        tv_name = (TextView) findViewById(R.id.name_text);
        tv_age = (TextView) findViewById(R.id.age_text);

        findViewById(R.id.submmit_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = new UserInfo();
                userInfo.setName(tv_name.getText().toString());
                userInfo.setAge(Integer.parseInt(tv_age.getText().toString()));
                testSQLiteDB.saveTestSQLiteInfo(userInfo);

                userInfoArrayList.add(userInfo);
                testSQLiteAdapter.notifyDataSetChanged();
            }
        });

        testSQLiteDB = TestSQLiteDB.getInstance(this);
    }
}
