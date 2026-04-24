package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;
    ListView listViewMovies;
    TextView tvDispName, tvDispYear, tvDispRating;
    ArrayList<String> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);
        listViewMovies = findViewById(R.id.listViewMovies);
        tvDispName = findViewById(R.id.tvDispName);
        tvDispYear = findViewById(R.id.tvDispYear);
        tvDispRating = findViewById(R.id.tvDispRating);
        movieList = new ArrayList<>();

        findViewById(R.id.btnAddReview).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddReviewActivity.class));
        });

        listViewMovies.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMovie = movieList.get(position);
            Cursor cursor = myDB.getMovieDetails(selectedMovie);

            if (cursor.moveToFirst()) {
                tvDispName.setText(cursor.getString(1)); // NAME
                tvDispYear.setText(cursor.getString(2)); // YEAR
                tvDispRating.setText(String.valueOf(cursor.getInt(3))); // RATING
            }
            cursor.close();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMoviesList();
    }

    private void loadMoviesList() {
        movieList.clear();
        Cursor cursor = myDB.getAllMovieNames();
        while (cursor.moveToNext()) {
            movieList.add(cursor.getString(0));
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieList);
        listViewMovies.setAdapter(adapter);
    }
}