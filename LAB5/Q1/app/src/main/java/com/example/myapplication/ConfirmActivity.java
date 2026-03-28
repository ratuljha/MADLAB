package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView tvDetails = findViewById(R.id.tvDisplayDetails);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        // 1. Get the data passed from MainActivity
        String type = getIntent().getStringExtra("TYPE");
        String vNum = getIntent().getStringExtra("VNUM");
        String rcNum = getIntent().getStringExtra("RCNUM");

        // 2. Display the data
        String displayMessage = "Vehicle Type: " + type + "\n" +
                "Vehicle Number: " + vNum + "\n" +
                "RC Number: " + rcNum;
        tvDetails.setText(displayMessage);

        // 3. Edit Button: Just close this screen to go back to the first one
        btnEdit.setOnClickListener(v -> {
            finish();
        });

        // 4. Confirm Button: Show random serial number Toast
        btnConfirm.setOnClickListener(v -> {
            // Generate a random 4 digit number
            int randomSerial = new Random().nextInt(9000) + 1000;
            Toast.makeText(this, "Confirmed! Allotment Serial: PRK-" + randomSerial, Toast.LENGTH_LONG).show();

            // Optional: disable button after clicking so they don't spam it
            btnConfirm.setEnabled(false);
        });
    }
}