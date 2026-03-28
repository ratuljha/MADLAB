package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText n1, n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        n1 = findViewById(R.id.num1);
        n2 = findViewById(R.id.num2);

        findViewById(R.id.btnAdd).setOnClickListener(v -> calculate('+'));
        findViewById(R.id.btnSub).setOnClickListener(v -> calculate('-'));
        findViewById(R.id.btnMul).setOnClickListener(v -> calculate('*'));
        findViewById(R.id.btnDiv).setOnClickListener(v -> calculate('/'));
    }

    private void calculate(char op) {
        double v1 = Double.parseDouble(n1.getText().toString());
        double v2 = Double.parseDouble(n2.getText().toString());
        double res = 0;

        if (op == '+') res = v1 + v2;
        else if (op == '-') res = v1 - v2;
        else if (op == '*') res = v1 * v2;
        else if (op == '/') res = v1 / v2;

        String out = v1 + " " + op + " " + v2 + " = " + res;

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("RESULT_KEY", out); // Passing data using key-value pairs [cite: 727]
        startActivity(intent);
    }
}