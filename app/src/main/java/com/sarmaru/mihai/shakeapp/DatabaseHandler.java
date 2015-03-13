package com.sarmaru.mihai.shakeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mihai Sarmaru on 30.12.2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quakeDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "quakes";

    private static final String KEY_ID = "id";
    private static final String KEY_UID = "uid";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_DEPTH = "depth";
    private static final String KEY_MAGNITUDE = "magnitude";
    private static final String KEY_TIME = "time";
    private static final String KEY_REGION = "region";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_UID + " INTEGER, " +
                KEY_LATITUDE + " REAL, " +
                KEY_LONGITUDE + " REAL, " +
                KEY_DEPTH + " INTEGER, " +
                KEY_MAGNITUDE + " REAL, " +
                KEY_TIME + " INTEGER, " +
                KEY_REGION + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertQuakeObject (QuakeObject quake) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = createQuakeContentValues(quake);
        return db.insert(TABLE_NAME, null, cv);
    }

    public QuakeObject getQuakeObject (int quakeUID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_UID + " = " + quakeUID, null);
        if (cursor.moveToFirst()) {
            return getQuakeObjectFromCursor(cursor);
        }
        return null;
    }

    public QuakeObject getLatestQuakeObject () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_UID + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            return getQuakeObjectFromCursor(cursor);
        }
        return null;
    }

    public List<QuakeObject> getQuakeList () {
        List<QuakeObject> quakeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                quakeList.add(getQuakeObjectFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return quakeList;
    }

    public List<QuakeObject> getLatestQuakesList (int latestQuakeID) {
        List<QuakeObject> quakeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " > " + latestQuakeID, null);
        if (cursor.moveToFirst()) {
            do {
                quakeList.add(getQuakeObjectFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return quakeList;
    }

    public boolean clearAllQuakes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        return getQuakeCount() == 0 ? true : false;
    }

    public int getQuakeCount () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor.getCount();
    }

    private QuakeObject getQuakeObjectFromCursor(Cursor cursor) {
        QuakeObject quake = new QuakeObject();
        quake.setId(cursor.getInt(1));
        quake.setLatitude(cursor.getDouble(2));
        quake.setLongitude(cursor.getDouble(3));
        quake.setDepth(cursor.getInt(4));
        quake.setMagnitude(cursor.getDouble(5));
        quake.setTime(cursor.getLong(6));
        quake.setRegion(cursor.getString(7));
        return quake;
    }

    private ContentValues createQuakeContentValues(QuakeObject quake) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_UID, quake.getId());
        cv.put(KEY_LATITUDE, quake.getLatitude());
        cv.put(KEY_LONGITUDE, quake.getLongitude());
        cv.put(KEY_DEPTH, quake.getDepth());
        cv.put(KEY_MAGNITUDE, quake.getMagnitude());
        cv.put(KEY_TIME, quake.getTime() / 1000); // erase millis
        cv.put(KEY_REGION, quake.getRegion());
        return cv;
    }
}
