package com.example.myapplication;

import android.app.DatePickerDialog; // Added missing import
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast; // Added missing import
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar; // Added missing import

public class TableActivity extends AppCompatActivity {
    String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // Retrieve data from intent
        name = getIntent().getStringExtra("NAME");
        id = getIntent().getStringExtra("ID");

        // Display data in TextViews
        if (name != null) ((TextView)findViewById(R.id.tvDispName)).setText(name);
        if (id != null) ((TextView)findViewById(R.id.tvDispId)).setText(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Adding both menu items so the logic works
        menu.add(Menu.NONE, 101, Menu.NONE, "Go to Page 3");
        menu.add(Menu.NONE, 102, Menu.NONE, "Show Date");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 101) {
            Intent intent = new Intent(this, InputActivity.class);
            // Safely pass all existing extras to the next page
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                intent.putExtras(extras);
            }
            startActivity(intent);
            return true;

        } else if (id == 102) {
            // Correct logic for DatePickerDialog
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (vw, y, m, d) -> {
                String selectedDate = d + "/" + (m + 1) + "/" + y;
                Toast.makeText(this, "Selected: " + selectedDate, Toast.LENGTH_SHORT).show();
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            return true;
        }

        // CRITICAL: Must return super for any unhandled items to prevent crashes
        return super.onOptionsItemSelected(item);
    }
}