package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Define Database and Table Name
    private static final String DB_NAME = "TasksDB.db";
    private static final String TABLE_NAME = "tasks_table";

    // Define Columns
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "DUE_DATE";
    private static final String COL_4 = "PRIORITY";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // This runs the very first time the DB is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DUE_DATE TEXT, PRIORITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 1. CREATE (Insert)
    public boolean insertTask(String name, String date, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, date);
        cv.put(COL_4, priority);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1; // returns true if successful
    }

    // 2. READ (Get All Tasks)
    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // 3. UPDATE
    public boolean updateTask(String id, String name, String date, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, name);
        cv.put(COL_3, date);
        cv.put(COL_4, priority);
        db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
        return true;
    }

    // 4. DELETE
    public Integer deleteTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
