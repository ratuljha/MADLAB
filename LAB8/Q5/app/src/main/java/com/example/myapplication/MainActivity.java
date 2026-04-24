package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail;
    SharedPreferences sharedPreferences;

    // Define constant keys for the key-value pairs
    private static final String PREF_NAME = "MyAppData";
    private static final String KEY_NAME = "saved_name";
    private static final String KEY_EMAIL = "saved_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        // Initialize SharedPreferences in PRIVATE mode (only this app can read it)
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // RETRIEVE DATA
        // The second parameter ("") is the default value if nothing is found in storage
        String retrievedName = sharedPreferences.getString(KEY_NAME, "");
        String retrievedEmail = sharedPreferences.getString(KEY_EMAIL, "");

        // Set the retrieved text back into the EditText fields
        etName.setText(retrievedName);
        etEmail.setText(retrievedEmail);
    }

    // This lifecycle method triggers automatically when the app is minimized or closed
    @Override
    protected void onPause() {
        super.onPause();

        // SAVE DATA
        // Create an Editor to modify the SharedPreferences file
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the current text from the screen into the editor
        editor.putString(KEY_NAME, etName.getText().toString());
        editor.putString(KEY_EMAIL, etEmail.getText().toString());

        // Apply saves the data asynchronously in the background
        editor.apply();
    }
}