package com.nextgis.mobile.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arka on 3/2/17.
 */

public class GisZipHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "layers.db";
    private final static String TABLE = "gisZips";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_FILE = "zips";

    public GisZipHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_FILE+" TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }


}
