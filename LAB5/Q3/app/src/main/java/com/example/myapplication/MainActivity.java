package com.example.myapplication;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerMovie = findViewById(R.id.spinnerMovie);
        Spinner spinnerTheatre = findViewById(R.id.spinnerTheatre);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnTime = findViewById(R.id.btnTime);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvTime = findViewById(R.id.tvTime);
        ToggleButton toggleTicketType = findViewById(R.id.toggleTicketType);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnBookNow = findViewById(R.id.btnBookNow);

        // 1. Setup Spinners
        String[] movies = {"Inception", "Interstellar", "The Dark Knight", "Dune"};
        String[] theatres = {"PVR Cinemas", "INOX", "Cinepolis", "IMAX"};
        spinnerMovie.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, movies));
        spinnerTheatre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, theatres));

        // 2. Setup Pickers
        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvDate.setText(selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                selectedTime = hourOfDay + ":" + minute;
                tvTime.setText(selectedTime);
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        // 3. The 12:00 PM Premium Logic Trick
        toggleTicketType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) { // Premium is selected
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // Gets 24-hour format time
                if (currentHour < 12) {
                    btnBookNow.setEnabled(false); // Disable button
                    Toast.makeText(this, "Premium tickets only available after 12 PM", Toast.LENGTH_SHORT).show();
                }
            } else { // Standard is selected
                btnBookNow.setEnabled(true); // Always enable for Standard
            }
        });

        // 4. Reset Button
        btnReset.setOnClickListener(v -> {
            spinnerMovie.setSelection(0);
            spinnerTheatre.setSelection(0);
            toggleTicketType.setChecked(false);
            btnBookNow.setEnabled(true);

            // Reset date to today
            Calendar c = Calendar.getInstance();
            selectedDate = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
            tvDate.setText("Date: " + selectedDate);

            selectedTime = "";
            tvTime.setText("No Time Selected");
        });

        // 5. Book Now Button (Validation + Intent)
        btnBookNow.setOnClickListener(v -> {
            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select Date and Time", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("MOVIE", spinnerMovie.getSelectedItem().toString());
                intent.putExtra("THEATRE", spinnerTheatre.getSelectedItem().toString());
                intent.putExtra("DATE", selectedDate);
                intent.putExtra("TIME", selectedTime);
                intent.putExtra("TYPE", toggleTicketType.isChecked() ? "Premium" : "Standard");
                startActivity(intent);
            }
        });
    }
}