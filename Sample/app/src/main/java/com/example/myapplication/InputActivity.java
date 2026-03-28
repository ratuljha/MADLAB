package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        findViewById(R.id.btnNext3).setOnClickListener(v -> {
            StringBuilder hobby = new StringBuilder();
            if (((CheckBox)findViewById(R.id.cbSports)).isChecked()) hobby.append("Sports ");
            if (((CheckBox)findViewById(R.id.cbMusic)).isChecked()) hobby.append("Music");

            Intent intent = new Intent(this, PickerActivity.class);

            // SAFETY CHECK: Only copy extras if they exist
            if (getIntent().getExtras() != null) {
                intent.putExtras(getIntent().getExtras());
            }

            intent.putExtra("HOBBY", hobby.toString());
            startActivity(intent);
        });
    }
}