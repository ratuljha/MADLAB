package com.example.myapplication;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        TextView tvBillDetails = findViewById(R.id.tvBillDetails);

        // Retrieve the bill string passed from MainActivity
        String billData = getIntent().getStringExtra("BILL_DATA");

        if (billData != null) {
            tvBillDetails.setText(billData);
        }
    }
}