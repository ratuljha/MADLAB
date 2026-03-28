package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerSource = findViewById(R.id.spinnerSource);
        Spinner spinnerDest = findViewById(R.id.spinnerDest);
        Button btnPickDate = findViewById(R.id.btnPickDate);
        TextView tvDateDisplay = findViewById(R.id.tvDateDisplay);
        ToggleButton toggleTripType = findViewById(R.id.toggleTripType);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnReset = findViewById(R.id.btnReset);

        // 1. Setup Spinners (The Lego Brick for Dropdowns)
        String[] cities = {"Bengaluru", "Mumbai", "Delhi", "Chennai", "Kolkata"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerSource.setAdapter(adapter);
        spinnerDest.setAdapter(adapter);

        // 2. Setup DatePicker (The Lego Brick for Dates)
        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                tvDateDisplay.setText("Date: " + selectedDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        // 3. Reset Button Logic
        btnReset.setOnClickListener(v -> {
            spinnerSource.setSelection(0);
            spinnerDest.setSelection(0);
            toggleTripType.setChecked(false);

            // Set date to current system date [cite: 2473]
            Calendar calendar = Calendar.getInstance();
            selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                    (calendar.get(Calendar.MONTH) + 1) + "/" +
                    calendar.get(Calendar.YEAR);
            tvDateDisplay.setText("Date: " + selectedDate);
        });

        // 4. Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            String source = spinnerSource.getSelectedItem().toString();
            String dest = spinnerDest.getSelectedItem().toString();
            String tripType = toggleTripType.isChecked() ? "Round-Trip" : "One-Way";

            if (source.equals(dest)) {
                Toast.makeText(this, "Source and Destination cannot be same!", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please select a date!", Toast.LENGTH_SHORT).show();
            } else {
                // Pass data to next screen
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("SOURCE", source);
                intent.putExtra("DEST", dest);
                intent.putExtra("DATE", selectedDate);
                intent.putExtra("TRIP", tripType);
                startActivity(intent);
            }
        });
    }
}