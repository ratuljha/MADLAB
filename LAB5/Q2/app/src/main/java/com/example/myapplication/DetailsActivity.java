package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView tvFinalDetails = findViewById(R.id.tvFinalDetails);

        // Retrieve data from Intent
        String source = getIntent().getStringExtra("SOURCE");
        String dest = getIntent().getStringExtra("DEST");
        String date = getIntent().getStringExtra("DATE");
        String trip = getIntent().getStringExtra("TRIP");

        // Format and display
        String ticket = "Trip Type: " + trip + "\n\n" +
                "From: " + source + "\n" +
                "To: " + dest + "\n\n" +
                "Date of Travel: " + date;

        tvFinalDetails.setText(ticket);
    }
}