package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper myDB;
    ListView listViewTasks;
    ArrayList<String> taskDisplayList; // Holds the text for the ListView
    ArrayList<String> taskIdList;      // Secretly holds the Database IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);
        listViewTasks = findViewById(R.id.listViewTasks);
        taskDisplayList = new ArrayList<>();
        taskIdList = new ArrayList<>();

        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
        });

        // Tell Android to look for long-presses on this list
        registerForContextMenu(listViewTasks);
    }

    // Refresh list every time we come back to this screen
    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void loadTasks() {
        taskDisplayList.clear();
        taskIdList.clear();
        Cursor cursor = myDB.getAllTasks();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No tasks found.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                taskIdList.add(cursor.getString(0)); // ID is column 0
                String display = "Task: " + cursor.getString(1) + "\nDue: " + cursor.getString(2) + " | Priority: " + cursor.getString(3);
                taskDisplayList.add(display);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskDisplayList);
        listViewTasks.setAdapter(adapter);
    }

    // --- CONTEXT MENU LOGIC (Edit / Delete) ---
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Task Options");
        menu.add(0, 1, 0, "Edit Task");
        menu.add(0, 2, 0, "Delete Task");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedId = taskIdList.get(info.position); // Get the actual DB ID of the clicked item

        if (item.getItemId() == 1) { // EDIT
            Cursor c = myDB.getAllTasks();
            c.moveToPosition(info.position);

            // Pass all existing data to the AddTaskActivity
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            intent.putExtra("ID", selectedId);
            intent.putExtra("NAME", c.getString(1));
            intent.putExtra("DATE", c.getString(2));
            intent.putExtra("PRIORITY", c.getString(3));
            startActivity(intent);
            return true;

        } else if (item.getItemId() == 2) { // DELETE
            new AlertDialog.Builder(this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        myDB.deleteTask(selectedId);
                        loadTasks(); // Refresh the list
                        Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}