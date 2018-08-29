package com.famgy.virtualpartner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String CREATE_TEST_TABLE = "create table TestSQLite " +
            "(Name TEXT NOT NULL, " +
            "Age INT NOT NULL)";

    public TestSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
