package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "MovieDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reviews (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, YEAR TEXT, RATING INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reviews");
        onCreate(db);
    }

    public boolean insertReview(String name, String year, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("YEAR", year);
        cv.put("RATING", rating);
        return db.insert("reviews", null, cv) != -1;
    }

    public Cursor getAllMovieNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT NAME FROM reviews", null);
    }

    public Cursor getMovieDetails(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM reviews WHERE NAME=?", new String[]{name});
    }
}