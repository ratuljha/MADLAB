/*
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║           ANDROID EXAM CHEAT SHEET — COPY PASTE READY          ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  Each section below is a self-contained snippet.               ║
 * ║  Copy the XML into your layout file.                           ║
 * ║  Copy the Java into your Activity's onCreate().                ║
 * ║  Don't forget the imports at the top of each section.          ║
 * ╚══════════════════════════════════════════════════════════════════╝
 *
 *  TABLE OF CONTENTS:
 *  1.  BUTTON
 *  2.  EDITTEXT (Text Input)
 *  3.  SPINNER (Dropdown)
 *  4.  CHECKBOX
 *  5.  RADIOBUTTON / RADIOGROUP
 *  6.  SWITCH / TOGGLEBUTTON
 *  7.  DATEPICKER DIALOG
 *  8.  TIMEPICKER DIALOG
 *  9.  LISTVIEW (Display a list)
 *  10. TOAST (Short message)
 *  11. ALERTDIALOG (Popup with buttons)
 *  12. INTENT — Navigate to another page
 *  13. INTENT — Pass data between pages
 *  14. DATABASE — Show all data in AlertDialog
 *  15. DATABASE — Show all data in ListView
 *  16. DATABASE — View DB file in Android Studio
 */


// ════════════════════════════════════════════════════════════════════
// 1. BUTTON
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <Button
        android:id="@+id/btnSubmit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Submit"/>
*/

// ── JAVA ──
/*
    // No extra imports needed

    Button btnSubmit = findViewById(R.id.btnSubmit);
    btnSubmit.setOnClickListener(v -> {
        // do something when clicked
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    });
*/


// ════════════════════════════════════════════════════════════════════
// 2. EDITTEXT (Text Input)
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <!-- Plain text -->
    <EditText
        android:id="@+id/etName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter name"/>

    <!-- Email -->
    <EditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter email"
        android:inputType="textEmailAddress"/>

    <!-- Password (hidden text) -->
    <EditText
        android:id="@+id/etPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter password"
        android:inputType="textPassword"/>

    <!-- Number only -->
    <EditText
        android:id="@+id/etAge"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter age"
        android:inputType="number"/>

    <!-- Phone number -->
    <EditText
        android:id="@+id/etPhone"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter phone"
        android:inputType="phone"/>

    <!-- Multi-line text -->
    <EditText
        android:id="@+id/etDescription"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter description"
        android:inputType="textMultiLine"
        android:minLines="3"/>
*/

// ── JAVA ──
/*
    // No extra imports needed

    EditText etName = findViewById(R.id.etName);

    // GET the text:
    String name = etName.getText().toString().trim();

    // SET text (e.g., pre-fill for editing):
    etName.setText("Some value");

    // CLEAR:
    etName.setText("");
*/


// ════════════════════════════════════════════════════════════════════
// 3. SPINNER (Dropdown)
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <Spinner
        android:id="@+id/spinnerCategory"
        android:layout_width="match_parent"
        android:layout_height="50dp"/>
*/

// ── JAVA ──
/*
    import android.widget.ArrayAdapter;
    import android.widget.Spinner;

    Spinner spinnerCategory = findViewById(R.id.spinnerCategory);

    // Option A: Hardcoded array
    String[] categories = {"Sports", "Music", "Tech", "Art"};
    spinnerCategory.setAdapter(new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item, categories));

    // Option B: From ArrayList
    // ArrayList<String> list = new ArrayList<>();
    // list.add("Sports"); list.add("Music");
    // spinnerCategory.setAdapter(new ArrayAdapter<>(this,
    //         android.R.layout.simple_spinner_dropdown_item, list));

    // GET selected value:
    String selected = spinnerCategory.getSelectedItem().toString();

    // SET selection by index (0 = first item):
    spinnerCategory.setSelection(0);

    // SET selection by finding the value:
    // for (int i = 0; i < categories.length; i++) {
    //     if (categories[i].equals("Tech")) {
    //         spinnerCategory.setSelection(i);
    //         break;
    //     }
    // }

    // OPTIONAL: Listen for selection changes
    // import android.widget.AdapterView;
    // spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    //     @Override
    //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //         String picked = parent.getItemAtPosition(position).toString();
    //         Toast.makeText(getApplicationContext(), "Selected: " + picked, Toast.LENGTH_SHORT).show();
    //     }
    //     @Override
    //     public void onNothingSelected(AdapterView<?> parent) {}
    // });
*/


// ════════════════════════════════════════════════════════════════════
// 4. CHECKBOX
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <CheckBox
        android:id="@+id/cbTerms"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="I agree to Terms"/>

    <CheckBox
        android:id="@+id/cbNewsletter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Subscribe to Newsletter"/>
*/

// ── JAVA ──
/*
    import android.widget.CheckBox;

    CheckBox cbTerms = findViewById(R.id.cbTerms);
    CheckBox cbNewsletter = findViewById(R.id.cbNewsletter);

    // CHECK if it's ticked:
    if (cbTerms.isChecked()) {
        // it's checked
    }

    // SET checked/unchecked:
    cbTerms.setChecked(true);   // tick it
    cbTerms.setChecked(false);  // untick it

    // COLLECT all checked values into a string:
    String selections = "";
    if (cbTerms.isChecked()) selections += "Terms Agreed, ";
    if (cbNewsletter.isChecked()) selections += "Newsletter, ";

    // LISTEN for changes:
    // cbTerms.setOnCheckedChangeListener((buttonView, isChecked) -> {
    //     if (isChecked) Toast.makeText(this, "Agreed!", Toast.LENGTH_SHORT).show();
    // });
*/


// ════════════════════════════════════════════════════════════════════
// 5. RADIOBUTTON / RADIOGROUP
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <!-- RadioGroup = only ONE can be selected at a time -->
    <RadioGroup
        android:id="@+id/rgGender"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <RadioButton
            android:id="@+id/rbMale"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Male"/>

        <RadioButton
            android:id="@+id/rbFemale"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Female"/>

        <RadioButton
            android:id="@+id/rbOther"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Other"/>
    </RadioGroup>
*/

// ── JAVA ──
/*
    import android.widget.RadioButton;
    import android.widget.RadioGroup;

    RadioGroup rgGender = findViewById(R.id.rgGender);

    // GET which one is selected:
    int selectedId = rgGender.getCheckedRadioButtonId();
    if (selectedId != -1) {   // -1 means nothing is selected
        RadioButton selected = findViewById(selectedId);
        String gender = selected.getText().toString();   // "Male", "Female", or "Other"
    }

    // CHECK a specific one by default:
    // rgGender.check(R.id.rbMale);

    // CLEAR selection:
    // rgGender.clearCheck();

    // LISTEN for changes:
    // rgGender.setOnCheckedChangeListener((group, checkedId) -> {
    //     RadioButton rb = findViewById(checkedId);
    //     Toast.makeText(this, "Selected: " + rb.getText(), Toast.LENGTH_SHORT).show();
    // });
*/


// ════════════════════════════════════════════════════════════════════
// 6. SWITCH / TOGGLEBUTTON
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <Switch
        android:id="@+id/switchNotifications"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Enable Notifications"/>

    <!-- OR use ToggleButton: -->
    <!--
    <ToggleButton
        android:id="@+id/toggleDarkMode"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textOn="Dark Mode ON"
        android:textOff="Dark Mode OFF"/>
    -->
*/

// ── JAVA ──
/*
    import android.widget.Switch;

    Switch switchNotif = findViewById(R.id.switchNotifications);

    // CHECK state:
    if (switchNotif.isChecked()) {
        // switch is ON
    }

    // SET state:
    switchNotif.setChecked(true);

    // LISTEN:
    // switchNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
    //     Toast.makeText(this, isChecked ? "ON" : "OFF", Toast.LENGTH_SHORT).show();
    // });
*/


// ════════════════════════════════════════════════════════════════════
// 7. DATEPICKER DIALOG
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginBottom="10dp">

        <Button
            android:id="@+id/btnPickDate"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Pick Date"/>

        <TextView
            android:id="@+id/tvDate"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="No date selected"
            android:layout_marginStart="15dp"
            android:textSize="16sp"/>
    </LinearLayout>
*/

// ── JAVA ──
/*
    import android.app.DatePickerDialog;
    import java.util.Calendar;

    // Declare at the top of the class (outside onCreate):
    String selectedDate = "";

    // Inside onCreate:
    TextView tvDate = findViewById(R.id.tvDate);

    findViewById(R.id.btnPickDate).setOnClickListener(v -> {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this,
            (view, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;   // month+1 because 0-indexed
                tvDate.setText(selectedDate);
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show();
    });

    // To use the date: just use the variable "selectedDate"
*/


// ════════════════════════════════════════════════════════════════════
// 8. TIMEPICKER DIALOG
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_marginBottom="10dp">

        <Button
            android:id="@+id/btnPickTime"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Pick Time"/>

        <TextView
            android:id="@+id/tvTime"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="No time selected"
            android:layout_marginStart="15dp"
            android:textSize="16sp"/>
    </LinearLayout>
*/

// ── JAVA ──
/*
    import android.app.TimePickerDialog;
    import java.util.Calendar;

    // Declare at the top of the class (outside onCreate):
    String selectedTime = "";

    // Inside onCreate:
    TextView tvTime = findViewById(R.id.tvTime);

    findViewById(R.id.btnPickTime).setOnClickListener(v -> {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this,
            (view, hourOfDay, minute) -> {
                // Simple 24hr format:
                selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                tvTime.setText(selectedTime);

                // OR 12hr format with AM/PM:
                // String amPm = (hourOfDay >= 12) ? "PM" : "AM";
                // int hr = (hourOfDay == 0) ? 12 : (hourOfDay > 12) ? hourOfDay - 12 : hourOfDay;
                // selectedTime = String.format("%02d:%02d %s", hr, minute, amPm);
                // tvTime.setText(selectedTime);
            },
            c.get(Calendar.HOUR_OF_DAY),
            c.get(Calendar.MINUTE),
            false    // false = 12hr clock, true = 24hr clock
        ).show();
    });
*/


// ════════════════════════════════════════════════════════════════════
// 9. LISTVIEW (Display a list of items)
// ════════════════════════════════════════════════════════════════════

// ── XML ──
/*
    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
*/

// ── JAVA ──
/*
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import java.util.ArrayList;

    ListView listView = findViewById(R.id.listView);
    ArrayList<String> items = new ArrayList<>();
    items.add("Item 1");
    items.add("Item 2");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, items);
    listView.setAdapter(adapter);

    // CLICK on item:
    listView.setOnItemClickListener((parent, view, position, id) -> {
        String clicked = items.get(position);
        Toast.makeText(this, "Clicked: " + clicked, Toast.LENGTH_SHORT).show();
    });

    // LONG PRESS on item (context menu — like LAB8 Q1):
    // See Section 5 in EventRegistrationActivity.java comments
*/


// ════════════════════════════════════════════════════════════════════
// 10. TOAST (Short popup message)
// ════════════════════════════════════════════════════════════════════

// ── JAVA ──
/*
    // No extra imports needed (android.widget.Toast)

    // Short toast (2 seconds):
    Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show();

    // Long toast (3.5 seconds):
    Toast.makeText(this, "This stays longer", Toast.LENGTH_LONG).show();
*/


// ════════════════════════════════════════════════════════════════════
// 11. ALERTDIALOG (Popup with Yes/No buttons)
// ════════════════════════════════════════════════════════════════════

// ── JAVA ──
/*
    import android.app.AlertDialog;

    // Simple confirmation dialog:
    new AlertDialog.Builder(this)
        .setTitle("Confirm")
        .setMessage("Are you sure?")
        .setPositiveButton("Yes", (dialog, which) -> {
            // user clicked Yes
            Toast.makeText(this, "Confirmed!", Toast.LENGTH_SHORT).show();
        })
        .setNegativeButton("No", null)   // null = just close the dialog
        .show();

    // Show a long message (e.g., database contents):
    new AlertDialog.Builder(this)
        .setTitle("Database Contents")
        .setMessage("Row 1: John\nRow 2: Jane\nRow 3: Bob")
        .setPositiveButton("OK", null)
        .show();
*/


// ════════════════════════════════════════════════════════════════════
// 12. INTENT — Navigate to another Activity
// ════════════════════════════════════════════════════════════════════

// ── JAVA ──
/*
    import android.content.Intent;

    // Go to another page:
    Intent intent = new Intent(this, NextActivity.class);
    startActivity(intent);

    // Go to another page AND close current one:
    Intent intent = new Intent(this, NextActivity.class);
    startActivity(intent);
    finish();   // closes current activity (can't go back to it)
*/


// ════════════════════════════════════════════════════════════════════
// 13. INTENT — Pass data between Activities
// ════════════════════════════════════════════════════════════════════

// ── JAVA (SENDING Activity) ──
/*
    Intent intent = new Intent(this, NextActivity.class);
    intent.putExtra("NAME", "John");              // String
    intent.putExtra("AGE", 21);                   // int
    intent.putExtra("IS_STUDENT", true);          // boolean
    startActivity(intent);
*/

// ── JAVA (RECEIVING Activity) ──
/*
    // In the receiving Activity's onCreate():
    String name = getIntent().getStringExtra("NAME");         // for String
    int age = getIntent().getIntExtra("AGE", 0);              // 0 = default if not found
    boolean isStudent = getIntent().getBooleanExtra("IS_STUDENT", false);
*/


// ════════════════════════════════════════════════════════════════════
// 14. DATABASE — Show ALL data in an AlertDialog popup
// ════════════════════════════════════════════════════════════════════
//
// USE THIS WHEN THEY ASK "DISPLAY DATABASE CONTENTS" or "VIEW DATA"
// Quick and easy — shows everything in a scrollable popup
//

// ── JAVA ──
/*
    import android.app.AlertDialog;
    import android.database.Cursor;

    // Put this in a button click or wherever you want to show the DB:
    Cursor cursor = myDB.getAllEvents();   // or getAllTasks(), getAllItems(), etc.
    if (cursor.getCount() == 0) {
        Toast.makeText(this, "Database is empty!", Toast.LENGTH_SHORT).show();
        return;
    }

    // Build a string with all rows
    StringBuilder sb = new StringBuilder();
    while (cursor.moveToNext()) {
        // Change column indices to match YOUR table
        // e.g., for events: 0=ID, 1=SERIAL_NO, 2=NAME, 3=DATE, 4=TIME, 5=LOCATION
        sb.append("ID: ").append(cursor.getString(0)).append("\n");
        sb.append("Name: ").append(cursor.getString(2)).append("\n");
        sb.append("Date: ").append(cursor.getString(3)).append("\n");
        sb.append("──────────────\n");
    }
    cursor.close();

    // Show in a popup
    new AlertDialog.Builder(this)
        .setTitle("All Records")
        .setMessage(sb.toString())
        .setPositiveButton("OK", null)
        .show();
*/


// ════════════════════════════════════════════════════════════════════
// 15. DATABASE — Show ALL data in a ListView
// ════════════════════════════════════════════════════════════════════
//
// USE THIS WHEN THEY ASK "DISPLAY IN A LIST"
// The standard pattern from LAB8 Q1
//

// ── JAVA ──
/*
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.database.Cursor;
    import java.util.ArrayList;

    // This is the FULL loadData() pattern — put this in a method and call from onResume():
    private void loadData() {
        ArrayList<String> displayList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();   // to track which row is which

        Cursor cursor = myDB.getAllEvents();   // change to your method
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                idList.add(cursor.getString(0));   // column 0 = ID or primary key

                // Build display string — change columns to match YOUR table
                String display = "Name: " + cursor.getString(2)
                    + "\nDate: " + cursor.getString(3);
                displayList.add(display);
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);
    }

    // Call from onResume so it refreshes when coming back from another page:
    // @Override
    // protected void onResume() {
    //     super.onResume();
    //     loadData();
    // }
*/


// ════════════════════════════════════════════════════════════════════
// 16. DATABASE — How to VIEW the SQLite DB file in Android Studio
// ════════════════════════════════════════════════════════════════════
//
// IF THE EXAMINER ASKS YOU TO "SHOW THE DATABASE" on screen:
//
// METHOD 1: App Inspection (Android Studio built-in)
//   1. Run the app on emulator
//   2. In Android Studio bottom bar → click "App Inspection"
//      (or go to View → Tool Windows → App Inspection)
//   3. Select your app from the dropdown
//   4. Click "Database Inspector" tab
//   5. You'll see all tables → click a table to see rows
//   6. You can run SQL queries directly there!
//
// METHOD 2: Device Explorer (manual way)
//   1. Run the app on emulator
//   2. In Android Studio → View → Tool Windows → Device Explorer
//   3. Navigate to: data → data → com.example.myapplication → databases
//   4. You'll see your .db file (e.g., EventAppDB.db)
//   5. Right-click → Save As → save to desktop
//   6. Open with "DB Browser for SQLite" (free tool)
//
// METHOD 3: Add a "Show DB" button in your app
//   Just use the AlertDialog pattern from Section 14 above.
//   Add a button in your XML, and on click show all data in a popup.
//   This is the EASIEST way to demonstrate to an examiner.
//
// METHOD 4: Log to Logcat
/*
    import android.util.Log;

    Cursor cursor = myDB.getAllEvents();
    while (cursor.moveToNext()) {
        Log.d("DB_DATA", "Row: " + cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2));
    }
    cursor.close();
    // Then check Logcat panel in Android Studio, filter by tag "DB_DATA"
*/

// ════════════════════════════════════════════════════════════════════
//  QUICK REFERENCE: Common imports you might need
// ════════════════════════════════════════════════════════════════════
/*
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.widget.Spinner;
    import android.widget.CheckBox;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Switch;
    import android.widget.ListView;
    import android.widget.ArrayAdapter;
    import android.widget.AdapterView;
    import android.content.Intent;
    import android.app.AlertDialog;
    import android.app.DatePickerDialog;
    import android.app.TimePickerDialog;
    import android.database.Cursor;
    import android.content.ContentValues;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;
    import android.view.ContextMenu;
    import android.view.MenuItem;
    import android.view.View;
    import androidx.appcompat.app.AppCompatActivity;
    import java.util.ArrayList;
    import java.util.Calendar;
*/


// ════════════════════════════════════════════════════════════════════
// 17. DATABASE CLOSE — myDB.close() in onDestroy()
// ════════════════════════════════════════════════════════════════════
//
// ALWAYS add this to every Activity that uses a DBHelper.
// Examiners check for this — it shows you handle resources properly.
//

// ── JAVA ──
/*
    // Step 1: Declare myDB as a CLASS-LEVEL variable (not inside onCreate)
    DBHelper myDB;

    // Step 2: In onCreate(), initialize it:
    myDB = new DBHelper(this);

    // Step 3: Add onDestroy() — this goes OUTSIDE onCreate(), at the end of the class:
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();   // Close database connection when activity is destroyed
    }

    // That's it. Just 3 lines. Add to EVERY activity that has a DBHelper.
*/


// ════════════════════════════════════════════════════════════════════
// 18. SHOW DATABASE TO EXAMINER — Step by Step (App Inspection)
// ════════════════════════════════════════════════════════════════════
//
// This is the method you used before. Exact steps:
//
//  1. Run app on emulator (make sure it's running, not stopped)
//  2. In Android Studio menu bar: View → Tool Windows → App Inspection
//  3. The App Inspection panel opens at the bottom
//  4. Make sure YOUR app is selected in the process dropdown
//     (it shows "com.example.myapplication")
//  5. Click on the "Database Inspector" tab (it might auto-select)
//  6. You'll see your database name (e.g., EventAppDB.db)
//  7. Expand it → you'll see all tables (users, events, registrations)
//  8. Click on any table → it shows all rows with all columns
//  9. You can also type SQL queries in the query bar at the top!
//     Example: SELECT * FROM users
//     Example: SELECT * FROM events WHERE NAME='Tech Fest'
//
// IMPORTANT: The app must be RUNNING on the emulator for this to work.
//            If you stop the app, the database inspector disconnects.
//
// IF APP INSPECTION DOESN'T SHOW YOUR DB:
//   - Make sure you've actually inserted data first (run the app, register a user, add an event)
//   - Try clicking the refresh button in the App Inspection panel
//   - Make sure you selected the right process (com.example.myapplication)
//
