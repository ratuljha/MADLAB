package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText etName = findViewById(R.id.etName);
        EditText etId = findViewById(R.id.etId);
        findViewById(R.id.btnNext1).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TableActivity.class);
            intent.putExtra("NAME", etName.getText().toString());
            intent.putExtra("ID", etId.getText().toString());
            startActivity(intent);
        });
    }
}