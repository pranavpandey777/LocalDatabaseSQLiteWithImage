package com.example.pranav.myprojectwithimage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE = "database.db";
    public static final int DATAVER = 5;
    public static final String TABLENAME = "infoprof";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "pass";
    public static final String EMAIL = "email";
    public static final String IMAGE = "image";

    public static final String CREATETABLE = "CREATE TABLE " + TABLENAME + "(" + NAME + " TEXT NOT NULL,"
            + PASSWORD + " TEXT NOT NULL," + EMAIL + " TEXT NOT NULL," + USERNAME + " TEXT PRIMARY KEY NOT NULL," + IMAGE + " BLOB)";


    public MyDatabase(Context context) {
        super(context, DATABASE, null, DATAVER);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
