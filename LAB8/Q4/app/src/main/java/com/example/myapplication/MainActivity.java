package com.example.myapplication; // Make sure this matches your actual package name

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper myDB;
    ListView listViewData;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);
        listViewData = findViewById(R.id.listViewData);
        dataList = new ArrayList<>();

        // Load the data initially
        loadData();

        // The refresh button allows us to load the data after we hack the DB externally
        findViewById(R.id.btnRefresh).setOnClickListener(v -> loadData());
    }

    private void loadData() {
        dataList.clear();
        Cursor cursor = myDB.getAllUsers();

        while (cursor.moveToNext()) {
            String row = "ID: " + cursor.getString(0) + " | Name: " + cursor.getString(1) + " | Dept: " + cursor.getString(2);
            dataList.add(row);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);
    }
}