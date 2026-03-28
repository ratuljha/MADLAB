package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOreo = findViewById(R.id.btnOreo);
        Button btnPie = findViewById(R.id.btnPie);
        Button btn10 = findViewById(R.id.btn10);

        // Set click listeners for each button
        // Note: Replace 'android.R.drawable.sym_def_app_icon' with your own image resources
        // e.g., R.drawable.oreo_icon

        btnOreo.setOnClickListener(v ->
                showVersionToast("Android Oreo", android.R.drawable.sym_def_app_icon));

        btnPie.setOnClickListener(v ->
                showVersionToast("Android Pie", android.R.drawable.star_on));

        btn10.setOnClickListener(v ->
                showVersionToast("Android 10", android.R.drawable.ic_menu_info_details));
    }

    // Function to inflate and show the Custom Toast
    private void showVersionToast(String versionName, int iconResId) {
        // 1. Inflate the Layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        // 2. Set the Text
        TextView text = layout.findViewById(R.id.toastText);
        text.setText(versionName);

        // 3. Set the Image
        ImageView image = layout.findViewById(R.id.toastIcon);
        image.setImageResource(iconResId);

        // 4. Create and Show the Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}