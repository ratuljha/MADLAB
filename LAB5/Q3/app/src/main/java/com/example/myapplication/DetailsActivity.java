package com.example.myapplication;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView tvTicketDetails = findViewById(R.id.tvTicketDetails);

        String movie = getIntent().getStringExtra("MOVIE");
        String theatre = getIntent().getStringExtra("THEATRE");
        String date = getIntent().getStringExtra("DATE");
        String time = getIntent().getStringExtra("TIME");
        String type = getIntent().getStringExtra("TYPE");

        // Generate random available seats for realism
        int randomSeats = new Random().nextInt(40) + 5;

        String details = "Movie: " + movie + "\n" +
                "Theatre: " + theatre + "\n" +
                "Ticket Type: " + type + "\n" +
                "Date: " + date + "\n" +
                "Showtime: " + time + "\n\n" +
                "Available Seats: " + randomSeats;

        tvTicketDetails.setText(details);
    }
}
