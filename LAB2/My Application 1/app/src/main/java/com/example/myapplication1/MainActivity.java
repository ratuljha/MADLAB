package com.example.myapplication1;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast("onCreate called"); // Initializing the activity
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("onStart called"); // Activity is becoming visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("onResume called"); // Activity is now in the foreground
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("onPause called"); // Activity is partially obscured
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("onStop called"); // Activity is no longer visible
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("onDestroy called"); // Final cleanup [cite: 295]
    }

    // Helper method to display Toast messages [cite: 640]
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}