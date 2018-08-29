package com.famgy.virtualpartner.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.famgy.modle.user.UserInfo;

import java.util.ArrayList;

public class TestSQLiteDB {

    private static final String DB_NAME = "test_sqlite_db";
    private static final int VERSION = 1;

    private SQLiteDatabase db_w;
    private SQLiteDatabase db_r;
    private static TestSQLiteDB testSQLiteDB;

    private TestSQLiteDB(Context context) {
        TestSQLiteOpenHelper dbHelper = new TestSQLiteOpenHelper(context, DB_NAME, null, VERSION);

        db_w = dbHelper.getWritableDatabase();
        db_r = dbHelper.getReadableDatabase();
    }

    public synchronized static TestSQLiteDB getInstance(Context context) {
        if (testSQLiteDB == null) {
            testSQLiteDB = new TestSQLiteDB(context);
        }

        return testSQLiteDB;
    }

    public void saveTestSQLiteInfo(UserInfo userInfo) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", userInfo.getName());
        contentValues.put("Age", userInfo.getAge());

        db_w.insert("TestSQLite", null, contentValues);
    }

    public ArrayList<UserInfo> getUserInfo() {
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();

        Cursor cursor = db_r.query("TestSQLite", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                UserInfo userInfo = new UserInfo();

                userInfo.setName(cursor.getString(cursor.getColumnIndex("Name")));
                userInfo.setAge(cursor.getInt(cursor.getColumnIndex("Age")));

                userInfoArrayList.add(userInfo);
            } while (cursor.moveToNext());
        }

        cursor.close();


        return userInfoArrayList;
    }
}
