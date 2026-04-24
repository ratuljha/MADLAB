package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText etItemName, etItemCost;
    Spinner spinnerItems;
    TextView tvTotal;
    ArrayList<String> itemList;
    int totalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);
        etItemName = findViewById(R.id.etItemName);
        etItemCost = findViewById(R.id.etItemCost);
        spinnerItems = findViewById(R.id.spinnerItems);
        tvTotal = findViewById(R.id.tvTotal);
        itemList = new ArrayList<>();

        loadSpinnerData();

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            String name = etItemName.getText().toString().trim();
            String costStr = etItemCost.getText().toString().trim();

            if (name.isEmpty() || costStr.isEmpty()) {
                Toast.makeText(this, "Enter name and cost", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = myDB.insertItem(name, Integer.parseInt(costStr));
            if (inserted) {
                Toast.makeText(this, "Item Added to DB", Toast.LENGTH_SHORT).show();
                etItemName.setText("");
                etItemCost.setText("");
                loadSpinnerData(); // Refresh spinner with new item
            }
        });

        findViewById(R.id.btnSelect).setOnClickListener(v -> {
            if (spinnerItems.getSelectedItem() != null) {
                String selectedItem = spinnerItems.getSelectedItem().toString();
                int cost = myDB.getItemCost(selectedItem);
                totalCost += cost;
                tvTotal.setText("Total Cost: " + totalCost);
            } else {
                Toast.makeText(this, "No items available in DB", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSpinnerData() {
        itemList.clear();
        Cursor cursor = myDB.getAllItems();
        while (cursor.moveToNext()) {
            itemList.add(cursor.getString(1)); // Column 1 is NAME
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemList);
        spinnerItems.setAdapter(adapter);
    }
}