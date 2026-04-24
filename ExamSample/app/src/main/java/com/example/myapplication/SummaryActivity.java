package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/*
 * ============================================================
 * PAGE 4: SUMMARY (Display all registered event details)
 * ============================================================
 *
 * WHAT THIS PAGE DOES:
 * - Shows the logged-in user's email
 * - Fetches all events the user registered for (using JOIN query in DBHelper)
 * - Displays event name, date, time, location, serial number in a ListView
 *
 * THIS IS THE SIMPLEST ACTIVITY — just read from DB and display.
 * Same loadTasks() / loadEvents() pattern but no buttons, no clicks.
 *
 * HOW TO MODIFY:
 * - Show more user info? → Add more TextViews in XML, set their text here
 * - Show count of events? → cursor.getCount() gives the number
 * - Different display format? → Change the string building in the while loop
 *
 * THE QUERY:
 *   getRegisteredEvents(email) uses INNER JOIN:
 *   "SELECT events.* FROM events INNER JOIN registrations
 *    ON events.SERIAL_NO = registrations.EVENT_SERIAL
 *    WHERE registrations.USER_EMAIL = ?"
 *
 *   This gives us full event details for only the events this user registered for.
 */

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Step 1: Initialize
        DBHelper myDB = new DBHelper(this);
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        // Step 2: Display user info
        TextView tvUserInfo = findViewById(R.id.tvUserInfo);
        tvUserInfo.setText("User: " + userEmail);

        /*
         * ── If question wants more user details (like name): ──
         * You'd need to store name in the users table first, then:
         *
         * Cursor userCursor = myDB.getUserByEmail(userEmail);  // add this method to DBHelper
         * if (userCursor.moveToFirst()) {
         *     String name = userCursor.getString(1);  // assuming column 1 is NAME
         *     tvUserInfo.setText("Name: " + name + "\nEmail: " + userEmail);
         * }
         * userCursor.close();
         */

        // Step 3: Load all events this user registered for
        ListView listView = findViewById(R.id.listViewSummary);
        ArrayList<String> summaryList = new ArrayList<>();

        // This cursor comes from the JOIN query — gives full event details
        Cursor cursor = myDB.getRegisteredEvents(userEmail);

        if (cursor.getCount() == 0) {
            summaryList.add("No events registered yet.");
        } else {
            while (cursor.moveToNext()) {
                /*
                 * Cursor columns (from "events" table via JOIN):
                 *   0 = ID (auto)
                 *   1 = SERIAL_NO
                 *   2 = NAME
                 *   3 = DATE
                 *   4 = TIME
                 *   5 = LOCATION
                 *
                 * TO MODIFY: Change the column indices to match YOUR table.
                 * getString(n) where n = column position starting from 0.
                 */
                String info = "Event: " + cursor.getString(2)         // NAME
                        + "\nSerial: " + cursor.getString(1)          // SERIAL_NO
                        + "\nDate: " + cursor.getString(3)            // DATE
                        + " | Time: " + cursor.getString(4)           // TIME
                        + "\nLocation: " + cursor.getString(5);       // LOCATION
                summaryList.add(info);
            }
        }
        cursor.close();

        // Step 4: Set adapter — same pattern as always
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                summaryList);
        listView.setAdapter(adapter);

        /*
         * ── BONUS: If question wants a "total events" count: ──
         *
         * TextView tvCount = findViewById(R.id.tvCount);  // add this to XML too
         * tvCount.setText("Total Events Registered: " + cursor.getCount());
         * // Note: call this BEFORE cursor.close()
         *
         * ── BONUS: If question wants to show this as plain text instead of ListView: ──
         * Replace the ListView with a single TextView in XML, then:
         *
         * StringBuilder sb = new StringBuilder();
         * while (cursor.moveToNext()) {
         *     sb.append("Event: ").append(cursor.getString(2)).append("\n");
         *     sb.append("Date: ").append(cursor.getString(3)).append("\n\n");
         * }
         * tvDetails.setText(sb.toString());
         */
    }
}
