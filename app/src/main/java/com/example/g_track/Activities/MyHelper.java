package com.example.g_track.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.g_track.Model.Alert;
import com.google.android.gms.games.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {
    private static final String dbName = "Token";
    private static final int version = 1;

    public MyHelper(Context context){
        super(context, dbName, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE ALERT (IDALERT INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ALERT");
        this.onCreate(db);
    }


    public ArrayList<Integer> allAlertId() {

        ArrayList<Integer> alertList = new ArrayList<Integer>();

        String query = "SELECT  * FROM ALERT";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int alertId = 0;

        if (cursor.moveToFirst()) {
            do {
                alertList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return alertList;
    }

    public void addToDB(int id) {
        Log.i("HELPER", "ADD to DB");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IDALERT", id);
        // insert
        db.insert("ALERT",null, values);
        Log.i("HELPER", "Saved S");
        db.close();
    }
}
