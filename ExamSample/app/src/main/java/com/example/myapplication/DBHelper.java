package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * ============================================================
 * DBHelper.java — THE SINGLE DATABASE FILE FOR THE WHOLE APP
 * ============================================================
 *
 * HOW TO MODIFY THIS FOR A DIFFERENT QUESTION:
 * 1. Change DB_NAME to something relevant (e.g., "HospitalDB.db")
 * 2. Change the CREATE TABLE statements in onCreate()
 * 3. Add/remove/rename methods to match your tables
 *
 * PATTERN FOR EVERY METHOD:
 *   INSERT → getWritableDatabase() → ContentValues → put() → insert()
 *   READ   → getReadableDatabase() → rawQuery("SELECT ...") → returns Cursor
 *   UPDATE → getWritableDatabase() → ContentValues → put() → update()
 *   DELETE → getWritableDatabase() → delete()
 *
 * IF APP CRASHES AFTER CHANGING TABLE STRUCTURE:
 *   → Uninstall app from emulator, then run again (old DB is cached)
 */

public class DBHelper extends SQLiteOpenHelper {

    // ── Change this name if question asks for a different app ──
    private static final String DB_NAME = "EventAppDB.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        // "null" = no cursor factory (always null)
        // "1"    = database version (if you change tables, increment this OR uninstall app)
    }

    // ── This runs ONCE when the database is first created ──
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * TABLE 1: users
         * - For login/register (Page 1)
         * - EMAIL is UNIQUE so same email can't register twice
         *
         * TO MODIFY: Change column names, add more columns
         * Example: If they ask for name too:
         *   "CREATE TABLE users (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT UNIQUE, PASSWORD TEXT)"
         */
        db.execSQL("CREATE TABLE users (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT UNIQUE, " +
                "PASSWORD TEXT)");

        /*
         * TABLE 2: events
         * - For event management (Page 2)
         * - SERIAL_NO is UNIQUE so we can find/update events by it
         *
         * TO MODIFY: Change columns based on what entity the question asks.
         * Example: If it's a "Book Library" app:
         *   "CREATE TABLE books (ID INTEGER PRIMARY KEY AUTOINCREMENT, ISBN TEXT UNIQUE, TITLE TEXT, AUTHOR TEXT, YEAR TEXT)"
         */
        db.execSQL("CREATE TABLE events (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SERIAL_NO TEXT UNIQUE, " +
                "NAME TEXT, " +
                "DATE TEXT, " +
                "TIME TEXT, " +
                "LOCATION TEXT)");

        /*
         * TABLE 3: registrations
         * - Links users to events (Page 3 & 4)
         * - This is a "junction table" / "linking table"
         *
         * TO MODIFY: Change column names to match your entities.
         * Example: For "Student-Course" app:
         *   "CREATE TABLE enrollments (ID INTEGER PRIMARY KEY AUTOINCREMENT, STUDENT_EMAIL TEXT, COURSE_CODE TEXT)"
         */
        db.execSQL("CREATE TABLE registrations (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_EMAIL TEXT, " +
                "EVENT_SERIAL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all tables and recreate — only runs when version number changes
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS registrations");
        onCreate(db);
    }


    // ╔══════════════════════════════════════════════════════════╗
    // ║  USER METHODS — Used by Page 1 (Login / Register)      ║
    // ╚══════════════════════════════════════════════════════════╝

    /*
     * Register a new user.
     * Same pattern as LAB8 Q1 insertTask():
     *   ContentValues → put columns → db.insert()
     *
     * Returns: true if success, false if failed (e.g., duplicate email)
     */
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EMAIL", email);       // column name → value
        cv.put("PASSWORD", password);
        long result = db.insert("users", null, cv);
        return result != -1;  // insert() returns -1 on failure
    }

    /*
     * Verify login credentials.
     * Uses rawQuery with WHERE clause — checks if email+password combo exists.
     *
     * The "?" placeholders prevent SQL injection and are replaced by the String[] values
     * in order. So EMAIL=? becomes EMAIL='test@test.com'
     */
    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE EMAIL=? AND PASSWORD=?",
                new String[]{email, password});
        boolean exists = cursor.getCount() > 0;  // if any rows found, login is valid
        cursor.close();
        return exists;
    }

    /*
     * Check if email is already taken (for registration validation).
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE EMAIL=?",
                new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    // ╔══════════════════════════════════════════════════════════╗
    // ║  EVENT METHODS — Used by Page 2 (Event Management)     ║
    // ╚══════════════════════════════════════════════════════════╝

    /*
     * Insert a new event.
     * Same as insertTask() from LAB8 — just more cv.put() calls for more columns.
     *
     * TO ADD MORE FIELDS: just add another cv.put("COLUMN_NAME", value);
     * Make sure the column exists in your CREATE TABLE above!
     */
    public boolean insertEvent(String serialNo, String name, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("SERIAL_NO", serialNo);
        cv.put("NAME", name);
        cv.put("DATE", date);
        cv.put("TIME", time);
        cv.put("LOCATION", location);
        long result = db.insert("events", null, cv);
        return result != -1;
    }

    /*
     * Update an existing event — found by its unique SERIAL_NO.
     * Same as LAB8 Q1 updateTask() but matches on SERIAL_NO instead of ID.
     *
     * db.update() params: (table, new values, WHERE clause, WHERE args)
     * Returns true if at least one row was updated.
     */
    public boolean updateEvent(String serialNo, String name, String date, String time, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DATE", date);
        cv.put("TIME", time);
        cv.put("LOCATION", location);
        int rows = db.update("events", cv, "SERIAL_NO = ?", new String[]{serialNo});
        return rows > 0;
    }

    /*
     * Find a single event by its serial number.
     * Returns a Cursor — use cursor.moveToFirst() then cursor.getString(columnIndex)
     *
     * Column indices for events table:
     *   0 = ID, 1 = SERIAL_NO, 2 = NAME, 3 = DATE, 4 = TIME, 5 = LOCATION
     */
    public Cursor getEventBySerial(String serialNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM events WHERE SERIAL_NO=?", new String[]{serialNo});
    }

    /*
     * Get ALL events — identical to LAB8 Q1 getAllTasks().
     * Used by Page 3 to display in ListView.
     */
    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM events", null);
    }

    /*
     * ── BONUS: Delete an event (uncomment if question asks for delete) ──
     *
     * public Integer deleteEvent(String serialNo) {
     *     SQLiteDatabase db = this.getWritableDatabase();
     *     return db.delete("events", "SERIAL_NO = ?", new String[]{serialNo});
     * }
     */


    // ╔══════════════════════════════════════════════════════════╗
    // ║  REGISTRATION METHODS — Used by Page 3 & 4             ║
    // ╚══════════════════════════════════════════════════════════╝

    /*
     * Register a user for an event.
     * Just an insert into the registrations (linking) table.
     */
    public boolean registerForEvent(String userEmail, String eventSerial) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("USER_EMAIL", userEmail);
        cv.put("EVENT_SERIAL", eventSerial);
        long result = db.insert("registrations", null, cv);
        return result != -1;
    }

    /*
     * Check if user already registered for this event (prevent duplicate registration).
     */
    public boolean isAlreadyRegistered(String userEmail, String eventSerial) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM registrations WHERE USER_EMAIL=? AND EVENT_SERIAL=?",
                new String[]{userEmail, eventSerial});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    /*
     * Get all events a specific user has registered for.
     * Uses INNER JOIN to combine events + registrations tables.
     *
     * INNER JOIN explained:
     *   - registrations table has EVENT_SERIAL
     *   - events table has SERIAL_NO
     *   - JOIN links them where EVENT_SERIAL = SERIAL_NO
     *   - Result: all event details for events this user registered for
     *
     * Cursor columns will be from events table:
     *   0 = ID, 1 = SERIAL_NO, 2 = NAME, 3 = DATE, 4 = TIME, 5 = LOCATION
     *
     * ── SIMPLER ALTERNATIVE (no JOIN, if you forget the syntax): ──
     * You can do it in 2 steps instead:
     *   Step 1: Get all EVENT_SERIAL from registrations WHERE USER_EMAIL=?
     *   Step 2: For each serial, getEventBySerial(serial)
     */
    public Cursor getRegisteredEvents(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT events.* FROM events " +
                "INNER JOIN registrations ON events.SERIAL_NO = registrations.EVENT_SERIAL " +
                "WHERE registrations.USER_EMAIL=?",
                new String[]{userEmail});
    }

    /*
     * ── BONUS: Get count of registrations for a user ──
     *
     * public int getRegistrationCount(String userEmail) {
     *     SQLiteDatabase db = this.getReadableDatabase();
     *     Cursor cursor = db.rawQuery(
     *         "SELECT COUNT(*) FROM registrations WHERE USER_EMAIL=?",
     *         new String[]{userEmail});
     *     cursor.moveToFirst();
     *     int count = cursor.getInt(0);
     *     cursor.close();
     *     return count;
     * }
     */
}
