package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the XML components to Java
        EditText urlInput = findViewById(R.id.urlInput);
        Button btnVisit = findViewById(R.id.btnVisit);

        btnVisit.setOnClickListener(view -> {
            String url = urlInput.getText().toString().trim();

            if (!url.isEmpty()) {
                // Ensure the URL has a protocol, otherwise Uri.parse may fail
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                try {
                    // Create an Implicit Intent to view the URL
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    // Handle cases where no browser is installed or URL is invalid
                    Toast.makeText(this, "Could not open link", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            }
        });
    }
}