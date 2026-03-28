package com.example.myapplication;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PickerActivity extends AppCompatActivity {
    String dt = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        Calendar c = Calendar.getInstance();
        findViewById(R.id.btnDate).setOnClickListener(v -> new DatePickerDialog(this, (vw, y, m, d) -> {
            dt = d + "/" + (m + 1) + "/" + y;
            ((TextView)findViewById(R.id.tvDt)).setText(dt);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show());

        findViewById(R.id.btnNext4).setOnClickListener(v -> {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtras(getIntent().getExtras());
            intent.putExtra("DATE", dt);
            startActivity(intent);
        });
    }
}