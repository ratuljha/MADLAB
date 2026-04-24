package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

/*
 * ============================================================
 * PAGE 2: EVENT MANAGEMENT (Add / Update Events)
 * ============================================================
 *
 * NOW USES DatePickerDialog AND TimePickerDialog
 * (same DatePickerDialog pattern as your LAB8 Q1 AddTaskActivity)
 *
 * HOW PICKERS WORK:
 *   1. User clicks "Pick Date" or "Pick Time" button
 *   2. A dialog pops up with a calendar/clock
 *   3. User selects → the callback fires → we save the value to a String variable
 *   4. We display the selected value in a TextView
 *   5. When saving to DB, we use the String variable (not getText from EditText)
 *
 * TO SWITCH BACK TO PLAIN EDITTEXT (if you don't need pickers):
 *   - In XML: uncomment the EditText, delete/comment the LinearLayout with Button+TextView
 *   - In Java: use etDate.getText().toString() instead of selectedDate
 *   - Remove the DatePickerDialog/TimePickerDialog button click listeners
 *   - Remove the Calendar import
 */

public class EventManagementActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText etSerial, etName, etLocation;
    TextView tvDate, tvTime;           // TextViews that display picked date/time
    String selectedDate = "";          // Stores picked date (same pattern as LAB8 Q1)
    String selectedTime = "";          // Stores picked time
    String userEmail;

    /*
     * ── IF USING PLAIN EDITTEXT INSTEAD OF PICKERS: ──
     * Replace the two lines above (tvDate/tvTime and selectedDate/selectedTime) with:
     *   EditText etDate, etTime;
     * And in onCreate, use:
     *   etDate = findViewById(R.id.etEventDate);
     *   etTime = findViewById(R.id.etEventTime);
     * And when saving, use:
     *   String date = etDate.getText().toString().trim();
     *   String time = etTime.getText().toString().trim();
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        // Step 1: Initialize
        myDB = new DBHelper(this);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        etSerial = findViewById(R.id.etSerialNo);
        etName = findViewById(R.id.etEventName);
        etLocation = findViewById(R.id.etEventLocation);
        tvDate = findViewById(R.id.tvDate);      // TextView, not EditText!
        tvTime = findViewById(R.id.tvTime);      // TextView, not EditText!


        // ╔══════════════════════════════════════════════════════════╗
        // ║  DATE PICKER — Copied from LAB8 Q1 AddTaskActivity     ║
        // ╚══════════════════════════════════════════════════════════╝
        /*
         * DatePickerDialog takes these parameters:
         *   1. context (this)
         *   2. listener — called when user picks a date, gives you year, month, day
         *   3. initial year to show
         *   4. initial month to show (0-indexed! January=0, December=11)
         *   5. initial day to show
         *
         * Calendar.getInstance() gives us today's date as the default.
         *
         * IMPORTANT: month is 0-indexed in the callback!
         *   So we do (month + 1) to display correctly.
         *   January comes as 0, February as 1, etc.
         */
        findViewById(R.id.btnPickDate).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();   // gets today's date
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        // month is 0-indexed, so add 1
                        // Format: DD/MM/YYYY (change format if question asks differently)
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        tvDate.setText(selectedDate);

                        /*
                         * ── OTHER DATE FORMATS (change if question specifies): ──
                         * YYYY-MM-DD:  selectedDate = year + "-" + (month+1) + "-" + dayOfMonth;
                         * MM/DD/YYYY:  selectedDate = (month+1) + "/" + dayOfMonth + "/" + year;
                         * With padding: String.format("%02d/%02d/%04d", dayOfMonth, month+1, year);
                         */
                    },
                    c.get(Calendar.YEAR),          // default year = current year
                    c.get(Calendar.MONTH),         // default month = current month
                    c.get(Calendar.DAY_OF_MONTH)   // default day = today
            ).show();   // .show() displays the dialog — don't forget this!
        });


        // ╔══════════════════════════════════════════════════════════╗
        // ║  TIME PICKER — Same pattern, different dialog           ║
        // ╚══════════════════════════════════════════════════════════╝
        /*
         * TimePickerDialog takes these parameters:
         *   1. context (this)
         *   2. listener — called when user picks a time, gives you hourOfDay, minute
         *   3. initial hour to show
         *   4. initial minute to show
         *   5. is24HourView — true for 24hr (14:00), false for 12hr (2:00 PM)
         *
         * hourOfDay is always in 24hr format in the callback (0-23).
         * We convert to 12hr format with AM/PM below.
         */
        findViewById(R.id.btnPickTime).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();   // gets current time
            new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        // Convert 24hr to 12hr format with AM/PM
                        String amPm;
                        int displayHour;
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                            displayHour = (hourOfDay == 12) ? 12 : hourOfDay - 12;
                        } else {
                            amPm = "AM";
                            displayHour = (hourOfDay == 0) ? 12 : hourOfDay;
                        }
                        // Format: "HH:MM AM/PM"
                        selectedTime = String.format("%02d:%02d %s", displayHour, minute, amPm);
                        tvTime.setText(selectedTime);

                        /*
                         * ── SIMPLER 24HR FORMAT (if you don't want AM/PM logic): ──
                         * selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                         * tvTime.setText(selectedTime);
                         */
                    },
                    c.get(Calendar.HOUR_OF_DAY),   // default hour = current hour
                    c.get(Calendar.MINUTE),         // default minute = current minute
                    false                           // false = 12hr clock with AM/PM selector
                                                    // true  = 24hr clock
            ).show();
        });


        // ── ADD EVENT BUTTON ──
        findViewById(R.id.btnAddEvent).setOnClickListener(v -> {
            String serial = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            // Date and time come from the picker variables, NOT from EditText
            String date = selectedDate;
            String time = selectedTime;

            // Validate — check all fields including picker values
            if (serial.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (myDB.insertEvent(serial, name, date, time, location)) {
                Toast.makeText(this, "Event Added!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed! Serial No might already exist.", Toast.LENGTH_SHORT).show();
            }
        });

        // ── UPDATE EVENT BUTTON ──
        findViewById(R.id.btnUpdateEvent).setOnClickListener(v -> {
            String serial = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String date = selectedDate;
            String time = selectedTime;

            if (serial.isEmpty()) {
                Toast.makeText(this, "Enter Serial No to update!", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor c = myDB.getEventBySerial(serial);
            if (c.getCount() == 0) {
                Toast.makeText(this, "No event found with that Serial No!", Toast.LENGTH_SHORT).show();
                c.close();
                return;
            }
            c.close();

            if (myDB.updateEvent(serial, name, date, time, location)) {
                Toast.makeText(this, "Event Updated!", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        });

        // ── GO TO EVENT REGISTRATION (Page 3) ──
        findViewById(R.id.btnGoToRegistration).setOnClickListener(v -> {
            Intent intent = new Intent(this, EventRegistrationActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }

    // Clear all fields + reset picker values
    private void clearFields() {
        etSerial.setText("");
        etName.setText("");
        etLocation.setText("");
        selectedDate = "";
        selectedTime = "";
        tvDate.setText("No date selected");
        tvTime.setText("No time selected");

        /*
         * ── IF USING PLAIN EDITTEXT, replace the date/time lines above with: ──
         * etDate.setText("");
         * etTime.setText("");
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }
}
