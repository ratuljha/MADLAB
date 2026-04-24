package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText etTaskName;
    TextView tvDate;
    Spinner spinnerPriority;
    String selectedDate = "";
    String updateId = null; // Will remain null if creating a new task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        myDB = new DBHelper(this);
        etTaskName = findViewById(R.id.etTaskName);
        tvDate = findViewById(R.id.tvDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);

        // Setup Spinner
        String[] priorities = {"High", "Medium", "Low"};
        spinnerPriority.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priorities));

        // Setup DatePicker
        findViewById(R.id.btnPickDate).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (vw, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                tvDate.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // CHECK IF WE ARE UPDATING (Data sent from MainActivity context menu)
        if (getIntent().hasExtra("ID")) {
            ((TextView)findViewById(R.id.tvFormTitle)).setText("Edit Task");
            updateId = getIntent().getStringExtra("ID");
            etTaskName.setText(getIntent().getStringExtra("NAME"));
            selectedDate = getIntent().getStringExtra("DATE");
            tvDate.setText(selectedDate);

            // Set the spinner to the saved priority
            String savedPriority = getIntent().getStringExtra("PRIORITY");
            if (savedPriority != null) {
                if (savedPriority.equals("High")) spinnerPriority.setSelection(0);
                else if (savedPriority.equals("Medium")) spinnerPriority.setSelection(1);
                else spinnerPriority.setSelection(2);
            }
        }

        // SAVE BUTTON LOGIC (Handles both Insert and Update)
        findViewById(R.id.btnSaveTask).setOnClickListener(v -> {
            String name = etTaskName.getText().toString();
            String priority = spinnerPriority.getSelectedItem().toString();

            if (name.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (updateId == null) {
                // It's a NEW task
                boolean isInserted = myDB.insertTask(name, selectedDate, priority);
                if (isInserted) Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show();
            } else {
                // It's an EDIT task
                boolean isUpdated = myDB.updateTask(updateId, name, selectedDate, priority);
                if (isUpdated) Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show();
            }

            finish(); // Closes this screen and goes back to MainActivity
        });
    }
}