package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/*
 * ============================================================
 * PAGE 3: EVENT REGISTRATION (Browse & Register for Events)
 * ============================================================
 *
 * WHAT THIS PAGE DOES:
 * - Shows ALL events from "events" table in a ListView
 * - User taps an event → registers them (inserts into "registrations" table)
 * - Has a button to go to Summary (Page 4)
 *
 * THIS IS YOUR LAB8 Q1 MAINACTIVITY — almost copy-paste:
 * - taskDisplayList → eventDisplayList
 * - taskIdList → eventSerialList
 * - loadTasks() → loadEvents()
 * - Long press context menu → Simple tap (setOnItemClickListener)
 *
 * THE LISTVIEW PATTERN (memorize this — it's in every question):
 *   1. Create ArrayList<String> for display text
 *   2. Create ArrayList<String> for IDs/keys (to know which item was clicked)
 *   3. Query DB with Cursor
 *   4. Loop: cursor.moveToNext() → add to both lists
 *   5. Create ArrayAdapter and set it on ListView
 *
 * HOW TO MODIFY:
 * - Different data? → Change the display string format in loadEvents()
 * - Long press instead of tap? → Use registerForContextMenu() like LAB8 Q1
 * - Want to show more details on click? → Use Intent to open a detail activity
 */

public class EventRegistrationActivity extends AppCompatActivity {

    DBHelper myDB;
    ListView listViewEvents;
    ArrayList<String> eventDisplayList;   // What the user SEES in the list
    ArrayList<String> eventSerialList;    // The serial numbers (hidden, for DB operations)
    String userEmail;                     // Who is logged in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);

        // Step 1: Initialize
        myDB = new DBHelper(this);
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        listViewEvents = findViewById(R.id.listViewEvents);
        eventDisplayList = new ArrayList<>();
        eventSerialList = new ArrayList<>();

        // Step 2: Load events into ListView
        loadEvents();

        // ── TAP ON EVENT TO REGISTER ──
        // In LAB8 you used registerForContextMenu (long press). Here it's a simple tap.
        listViewEvents.setOnItemClickListener((parent, view, position, id) -> {
            // position = which item in the list was clicked (0, 1, 2, ...)
            String serial = eventSerialList.get(position);   // get the serial of clicked event

            // Check if already registered (prevent duplicates)
            if (myDB.isAlreadyRegistered(userEmail, serial)) {
                Toast.makeText(this, "Already registered for this event!", Toast.LENGTH_SHORT).show();
            }
            // Register
            else if (myDB.registerForEvent(userEmail, serial)) {
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * ── ALTERNATIVE: Long Press Menu (like LAB8 Q1) ──
         * If the question specifically asks for long press instead of tap:
         *
         * // In onCreate():
         * registerForContextMenu(listViewEvents);
         *
         * // Then add these two methods outside onCreate():
         *
         * @Override
         * public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         *     super.onCreateContextMenu(menu, v, menuInfo);
         *     menu.setHeaderTitle("Options");
         *     menu.add(0, 1, 0, "Register for Event");
         *     // menu.add(0, 2, 0, "View Details");  // add more options if needed
         * }
         *
         * @Override
         * public boolean onContextItemSelected(MenuItem item) {
         *     AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
         *     String serial = eventSerialList.get(info.position);
         *     if (item.getItemId() == 1) {
         *         if (myDB.registerForEvent(userEmail, serial)) {
         *             Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show();
         *         }
         *         return true;
         *     }
         *     return super.onContextItemSelected(item);
         * }
         *
         * Don't forget these imports if using context menu:
         * import android.view.ContextMenu;
         * import android.view.MenuItem;
         * import android.view.View;
         * import android.widget.AdapterView;
         */

        // ── GO TO SUMMARY (Page 4) ──
        findViewById(R.id.btnGoToSummary).setOnClickListener(v -> {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }

    // Refresh list every time this page becomes visible
    // (In case new events were added on Page 2)
    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    /*
     * loadEvents() — THE LISTVIEW LOADING PATTERN
     *
     * This is the EXACT same pattern as loadTasks() in your LAB8 Q1:
     * 1. Clear both lists
     * 2. Get cursor from DB
     * 3. Loop through cursor rows
     * 4. Add display text to one list, ID/serial to another
     * 5. Create adapter and set it
     *
     * Cursor column indices for "events" table:
     *   0 = ID (auto), 1 = SERIAL_NO, 2 = NAME, 3 = DATE, 4 = TIME, 5 = LOCATION
     *
     * TO MODIFY: Just change cursor.getString(index) to match your table columns.
     */
    private void loadEvents() {
        eventDisplayList.clear();
        eventSerialList.clear();
        Cursor cursor = myDB.getAllEvents();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No events available.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                // Save the serial number (hidden from user, used for DB operations)
                eventSerialList.add(cursor.getString(1));   // column 1 = SERIAL_NO

                // Build the display string (what user sees in the list)
                String display = cursor.getString(2)                    // column 2 = NAME
                        + "\nDate: " + cursor.getString(3)              // column 3 = DATE
                        + " | Time: " + cursor.getString(4)             // column 4 = TIME
                        + "\nLocation: " + cursor.getString(5);         // column 5 = LOCATION
                eventDisplayList.add(display);
            }
        }
        cursor.close();   // Always close cursor when done

        // Create adapter and attach to ListView — same as LAB8
        // android.R.layout.simple_list_item_1 = built-in single-text-line layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventDisplayList);
        listViewEvents.setAdapter(adapter);
    }
}
