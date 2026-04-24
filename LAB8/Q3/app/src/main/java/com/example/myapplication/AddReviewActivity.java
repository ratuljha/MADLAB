package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddReviewActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText etMovieName, etMovieYear;
    Spinner spinnerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        myDB = new DBHelper(this);
        etMovieName = findViewById(R.id.etMovieName);
        etMovieYear = findViewById(R.id.etMovieYear);
        spinnerRating = findViewById(R.id.spinnerRating);

        // Populate Spinner with 1-5 ratings
        String[] ratings = {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ratings);
        spinnerRating.setAdapter(adapter);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = etMovieName.getText().toString().trim();
            String year = etMovieYear.getText().toString().trim();

            if (name.isEmpty() || year.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int rating = Integer.parseInt(spinnerRating.getSelectedItem().toString());

            boolean isInserted = myDB.insertReview(name, year, rating);
            if (isInserted) {
                Toast.makeText(this, "Review Saved", Toast.LENGTH_SHORT).show();
                finish(); // Close activity and return to MainActivity
            } else {
                Toast.makeText(this, "Error saving review", Toast.LENGTH_SHORT).show();
            }
        });
    }
}