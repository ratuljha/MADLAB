package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/*
 * ============================================================
 * PAGE 1: LOGIN / REGISTER (This is the LAUNCHER activity)
 * ============================================================
 *
 * WHAT THIS PAGE DOES:
 * - Two EditTexts (email, password) + Two Buttons (login, register)
 * - Register: saves email+password to "users" table in SQLite
 * - Login: checks if email+password exist in "users" table
 * - On successful login: go to Page 2 (EventManagementActivity)
 *
 * HOW TO MODIFY:
 * - If they want a "Name" field too: add EditText in XML, add column in DB, add cv.put()
 * - If they want "Forgot Password": add a button that queries DB by email only
 * - The key thing: registerUser() and checkLogin() in DBHelper handle all the DB work
 *
 * PATTERN USED:
 *   findViewById() → setOnClickListener() → get text → call DBHelper method → show Toast
 *
 * NAVIGATION:
 *   Login success → Intent to EventManagementActivity
 *   We pass USER_EMAIL via intent.putExtra() so all pages know WHO is logged in
 */

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;           // Database helper — same variable name as LAB8
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Step 1: Initialize DB and find views (same as every LAB8 question)
        myDB = new DBHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // ── LOGIN BUTTON ──
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            // Validate inputs
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;   // return = stop here, don't continue
            }

            // Check against database
            if (myDB.checkLogin(email, pass)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Navigate to Page 2 — pass email so other pages know who's logged in
                Intent intent = new Intent(this, EventManagementActivity.class);
                intent.putExtra("USER_EMAIL", email);   // putExtra = attach data to the intent
                startActivity(intent);

            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // ── REGISTER BUTTON ──
        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if email already taken
            if (myDB.checkEmailExists(email)) {
                Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT).show();
            }
            // Try to register
            else if (myDB.registerUser(email, pass)) {
                Toast.makeText(this, "Registration Successful! Now login.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * ── BONUS: If question asks for a "Register" on a separate page: ──
         * Instead of handling register here, navigate to a RegisterActivity:
         *
         * findViewById(R.id.btnRegister).setOnClickListener(v -> {
         *     startActivity(new Intent(this, RegisterActivity.class));
         * });
         *
         * Then create RegisterActivity.java with the register logic.
         * Don't forget to add <activity android:name=".RegisterActivity" /> in AndroidManifest.xml
         */
    }

    /*
     * onDestroy() — called when this Activity is being killed.
     * We close the database connection here to free resources.
     * ALWAYS add this in every Activity that uses DBHelper.
     *
     * Copy-paste this block into any Activity that has: DBHelper myDB;
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();   // Close database connection
    }
}
