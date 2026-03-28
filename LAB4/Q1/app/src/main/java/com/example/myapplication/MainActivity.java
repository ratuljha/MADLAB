package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStandard = findViewById(R.id.btnStandard);
        ToggleButton btnToggle = findViewById(R.id.btnToggle);

        // 1. Standard Button Click Listener
        btnStandard.setOnClickListener(v -> {
            showCustomToast("Camera Action", android.R.drawable.ic_menu_camera);
        });

        // 2. Toggle Button Click Listener
        btnToggle.setOnClickListener(v -> {
            if (btnToggle.isChecked()) {
                showCustomToast("Toggle is ON", android.R.drawable.ic_menu_call);
            } else {
                showCustomToast("Toggle is OFF", android.R.drawable.ic_delete);
            }
        });
    }

    // Helper method to create and show the Custom Toast [cite: 620]
    private void showCustomToast(String message, int imageResId) {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        // Set the text and image
        TextView text = layout.findViewById(R.id.toastText);
        ImageView image = layout.findViewById(R.id.toastImage);

        text.setText(message);
        image.setImageResource(imageResId);

        // Create and show the Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout); // Bind the custom layout to the Toast
        toast.show();
    }
}