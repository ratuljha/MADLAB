package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/*
 * ============================================================
 * PAGE 2: EVENT MANAGEMENT (Add / Update Events)
 * ============================================================
 *
 * WHAT THIS PAGE DOES:
 * - Form with fields: Serial No, Name, Date, Time, Location
 * - "Add" button → inserts new event into "events" table
 * - "Update" button → finds event by Serial No and updates it
 * - "Go to Registration" → navigates to Page 3
 *
 * THIS IS BASICALLY YOUR LAB8 Q1 AddTaskActivity:
 * - Instead of task name + date + priority → event serial + name + date + time + location
 * - Instead of checking updateId → checking serial number in DB
 * - Same pattern: get text from EditTexts → call DBHelper method → show Toast
 *
 * HOW TO MODIFY:
 * - Different fields? → Change EditTexts in XML, change cv.put() in DBHelper, change getString() here
 * - Want a Spinner (dropdown) instead of EditText? → See LAB8 Q1 spinnerPriority pattern
 * - Want DatePicker? → Copy the DatePickerDialog code from LAB8 Q1 AddTaskActivity
 */

public class EventManagementActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText etSerial, etName, etDate, etTime, etLocation;
    String userEmail;   // received from login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        // Step 1: Initialize DB and get logged-in user's email
        myDB = new DBHelper(this);
        userEmail = getIntent().getStringExtra("USER_EMAIL");   // received from Page 1

        // Step 2: Link all EditTexts
        etSerial = findViewById(R.id.etSerialNo);
        etName = findViewById(R.id.etEventName);
        etDate = findViewById(R.id.etEventDate);
        etTime = findViewById(R.id.etEventTime);
        etLocation = findViewById(R.id.etEventLocation);

        // ── ADD EVENT BUTTON ──
        // Same as your LAB8 "Save Task" button, just different fields
        findViewById(R.id.btnAddEvent).setOnClickListener(v -> {
            String serial = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            // Validate — make sure nothing is empty
            if (serial.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert into DB — same as myDB.insertTask() from LAB8
            if (myDB.insertEvent(serial, name, date, time, location)) {
                Toast.makeText(this, "Event Added!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                // This fails if SERIAL_NO already exists (because of UNIQUE constraint)
                Toast.makeText(this, "Failed! Serial No might already exist.", Toast.LENGTH_SHORT).show();
            }
        });

        // ── UPDATE EVENT BUTTON ──
        // Same as your LAB8 update logic, but finds by Serial No instead of ID
        findViewById(R.id.btnUpdateEvent).setOnClickListener(v -> {
            String serial = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (serial.isEmpty()) {
                Toast.makeText(this, "Enter Serial No to update!", Toast.LENGTH_SHORT).show();
                return;
            }

            // First check if event with this serial exists
            Cursor c = myDB.getEventBySerial(serial);
            if (c.getCount() == 0) {
                Toast.makeText(this, "No event found with that Serial No!", Toast.LENGTH_SHORT).show();
                c.close();
                return;
            }
            c.close();

            // Update it
            if (myDB.updateEvent(serial, name, date, time, location)) {
                Toast.makeText(this, "Event Updated!", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        });

        /*
         * ── BONUS: "Find Event" button to auto-fill form (uncomment if needed): ──
         *
         * findViewById(R.id.btnFindEvent).setOnClickListener(v -> {
         *     String serial = etSerial.getText().toString().trim();
         *     if (serial.isEmpty()) {
         *         Toast.makeText(this, "Enter Serial No", Toast.LENGTH_SHORT).show();
         *         return;
         *     }
         *     Cursor c = myDB.getEventBySerial(serial);
         *     if (c.moveToFirst()) {
         *         // Column indices: 0=ID, 1=SERIAL_NO, 2=NAME, 3=DATE, 4=TIME, 5=LOCATION
         *         etName.setText(c.getString(2));
         *         etDate.setText(c.getString(3));
         *         etTime.setText(c.getString(4));
         *         etLocation.setText(c.getString(5));
         *         Toast.makeText(this, "Event found!", Toast.LENGTH_SHORT).show();
         *     } else {
         *         Toast.makeText(this, "No event with that serial", Toast.LENGTH_SHORT).show();
         *     }
         *     c.close();
         * });
         */

        // ── GO TO EVENT REGISTRATION (Page 3) ──
        findViewById(R.id.btnGoToRegistration).setOnClickListener(v -> {
            Intent intent = new Intent(this, EventRegistrationActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);   // pass email forward
            startActivity(intent);
        });
    }

    // Helper to clear all fields after successful add/update
    private void clearFields() {
        etSerial.setText("");
        etName.setText("");
        etDate.setText("");
        etTime.setText("");
        etLocation.setText("");
    }
}
