package com.example.myapplication;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        findViewById(R.id.btnShowPop).setOnClickListener(v -> {
            String summary = "Name: " + getIntent().getStringExtra("NAME") +
                    "\nHobbies: " + getIntent().getStringExtra("HOBBY") +
                    "\nDate: " + getIntent().getStringExtra("DATE");

            new AlertDialog.Builder(this)
                    .setTitle("Final Details")
                    .setMessage(summary)
                    .setPositiveButton("OK", null)
                    .show();
        });
    }
}