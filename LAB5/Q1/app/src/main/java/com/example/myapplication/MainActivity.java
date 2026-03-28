package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinnerVehicleType);
        EditText etVehicleNum = findViewById(R.id.etVehicleNumber);
        EditText etRcNum = findViewById(R.id.etRcNumber);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // 1. Setup the Spinner
        String[] vehicleTypes = {"Car", "Bike", "Scooter", "Bus"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicleTypes);
        spinner.setAdapter(adapter);

        // 2. Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            String type = spinner.getSelectedItem().toString();
            String vNum = etVehicleNum.getText().toString();
            String rcNum = etRcNum.getText().toString();

            if (vNum.isEmpty() || rcNum.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                // Pass data to the next screen
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                intent.putExtra("TYPE", type);
                intent.putExtra("VNUM", vNum);
                intent.putExtra("RCNUM", rcNum);
                startActivity(intent);
            }
        });
    }
}