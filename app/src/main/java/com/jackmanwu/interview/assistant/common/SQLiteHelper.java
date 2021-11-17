package com.jackmanwu.interview.assistant.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "interview.assistant.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_ARTICLE = "article";

    private static final String CATEGORY_CREATE_SQL = "create table " + TABLE_CATEGORY + "("
            + "id integer primary key autoincrement,"
            + "name varchar(10) not null default '');";

    private static final String ARTICLE_CREATE_SQL = "create table " + TABLE_ARTICLE + "("
            + "id integer primary key autoincrement,"
            + "cid integer not null default 0,"
            + "title varchar(100) not null default '',"
            + "url varchar(300) not null default '')";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CATEGORY_CREATE_SQL);
        sqLiteDatabase.execSQL(ARTICLE_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                //do something
                break;
            case 2:
                //do something
                break;
            default:
                break;
        }
    }
}
