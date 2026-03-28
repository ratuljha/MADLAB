package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvPageTitle, tvContentDetails;
    ImageView ivContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPageTitle = findViewById(R.id.tvPageTitle);
        tvContentDetails = findViewById(R.id.tvContentDetails);
        ivContent = findViewById(R.id.ivContent);
    }

    // LEGO BRICK 1: Load the Menu onto the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // LEGO BRICK 2: Listen for clicks on the Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Hide the image by default on every click, we only want it for Trainers
        ivContent.setVisibility(View.GONE);

        // Icon Based Menu Items
        if (id == R.id.menu_home) {
            tvPageTitle.setText("Homepage");
            tvContentDetails.setText("Welcome back to XYZ Fitness Center!\n\nYour journey to a healthier life starts here.");
            return true;

        } else if (id == R.id.menu_contact) {
            tvPageTitle.setText("Contact Us");
            tvContentDetails.setText("Call us: +91 9876543210\nEmail: fit@xyzcenter.com\nAddress: 123 Muscle Street, Bengaluru");
            return true;

        } else if (id == R.id.menu_about) {
            tvPageTitle.setText("About Us");
            tvContentDetails.setText("XYZ Fitness has been transforming lives since 2010. We feature state-of-the-art equipment and world-class trainers.");
            return true;

            // Text Based Menu Items (The dropdown overflow)
        } else if (id == R.id.menu_workout) {
            tvPageTitle.setText("Workout Plans");
            tvContentDetails.setText("1. Weight Loss Program (Fat Burn)\n2. Cardio Blast\n3. Muscle Gain & Hypertrophy\n4. Yoga & Flexibility");
            return true;

        } else if (id == R.id.menu_trainers) {
            tvPageTitle.setText("Our Trainers");
            // Show the image view and set a built-in Android icon as the "Trainer Photo"
            ivContent.setVisibility(View.VISIBLE);
            ivContent.setImageResource(android.R.drawable.sym_contact_card);
            tvContentDetails.setText("Trainer: John Doe\nSpecialization: Weightlifting & Strength\n\nTrainer: Jane Smith\nSpecialization: Yoga & Pilates");
            return true;

        } else if (id == R.id.menu_membership) {
            tvPageTitle.setText("Membership Packages");
            tvContentDetails.setText("Bronze: ₹1000/month (Gym only)\n\nSilver: ₹2500/month (Gym + Cardio)\n\nGold: ₹4000/month (All access + Personal Trainer)");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}