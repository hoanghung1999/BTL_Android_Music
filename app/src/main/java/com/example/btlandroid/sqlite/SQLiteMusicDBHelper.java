package com.example.btlandroid.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteMusicDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "music_app";
    private static final int DB_VERSION = 1;

    private static final String CREAT_TABLE_USER="CREATE TABLE user(\n" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "name TEXt,\n" +
            "email TEXT UNIQUE,\n" +
            "password TEXT,\n" +
            "avatar TEXT,\n" +
            "idfacebook TEXT,\n" +
            "idgoogle TEXT,\n" +
            "online INTEGER\n" +
            ")";

    private static final String CREAT_TABLE_BAIHAT="CREATE TABLE baihat(\n" +
            "id InTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "image TEXt,\n" +
            "name TEXT,\n" +
            "singer TEXT,\n" +
            "file TEXT\n" +
            ")";
    private  static  final  String CREAT_TABLE_YEUTHICH="CREATE TABLE yeuthich(\n" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  iduser INTEGER,\n" +
            "  idbaihat INTEGER\n" +
            ")";


    public SQLiteMusicDBHelper(@Nullable Context context) {
        super(context,DB_NAME,null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREAT_TABLE_USER);
    db.execSQL(CREAT_TABLE_BAIHAT);
    db.execSQL(CREAT_TABLE_YEUTHICH);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
