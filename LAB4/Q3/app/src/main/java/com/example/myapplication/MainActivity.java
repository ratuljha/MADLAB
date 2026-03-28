package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButtonMode;
    private ImageView imageViewMode;
    private Button buttonChangeMode;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // STEP 2: Initialize - Find all views from the layout
        toggleButtonMode = findViewById(R.id.toggleButtonMode);
        imageViewMode = findViewById(R.id.imageViewMode);
        buttonChangeMode = findViewById(R.id.buttonChangeMode);
        textViewStatus = findViewById(R.id.textViewStatus);

        // STEP 3: Set up ToggleButton listener
        toggleButtonMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // isChecked = true means Wi-Fi (ON state)
                // isChecked = false means Mobile Data (OFF state)
                updateMode(isChecked);
            }
        });

        buttonChangeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Flip the toggle state
                toggleButtonMode.setChecked(!toggleButtonMode.isChecked());
            }
        });

        // Initialize UI based on current state
        updateMode(toggleButtonMode.isChecked());
    }

    private void updateMode(boolean isWifiMode) {
        if (isWifiMode) {
            // Ensure you have ic_wifi_foreground in res/drawable
            imageViewMode.setImageResource(R.drawable.ic_wifi_foreground);
            textViewStatus.setText("Current Mode: Wi-Fi");
            Toast.makeText(this, "Switched to Wi-Fi Mode", Toast.LENGTH_SHORT).show();
        } else {
            // Ensure you have ic_mobile_data_foreground in res/drawable
            imageViewMode.setImageResource(R.drawable.ic_mobile_data_foreground);
            textViewStatus.setText("Current Mode: Mobile Data");
            Toast.makeText(this, "Switched to Mobile Data Mode", Toast.LENGTH_SHORT).show();
        }
    }
}