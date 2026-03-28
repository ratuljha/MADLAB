package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize the ListView
        ListView listView = findViewById(R.id.sportsListView);

        // 2. Define the data source (Array of Sports)
        String[] sports = {
                "Cricket", "Football", "Basketball", "Tennis",
                "Badminton", "Hockey", "Volleyball", "Table Tennis",
                "Baseball", "Rugby"
        };

        // 3. Create an ArrayAdapter to bind data to the ListView [cite: 415]
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sports
        );

        // 4. Set the adapter to the ListView [cite: 416]
        listView.setAdapter(adapter);

        // 5. Set an OnItemClickListener to handle clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text
                String selectedSport = sports[position];

                // Display the selected sport in a Toast message [cite: 635]
                Toast.makeText(MainActivity.this, "Selected: " + selectedSport, Toast.LENGTH_SHORT).show();
            }
        });
    }
}