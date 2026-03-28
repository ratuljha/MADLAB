package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbCoffee, cbFries;
    Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbCoffee = findViewById(R.id.cbCoffee);
        cbFries = findViewById(R.id.cbFries);
        btnOrder = findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(v -> {
            StringBuilder bill = new StringBuilder();
            int total = 0;

            // Check items and calculate total
            if (cbPizza.isChecked()) {
                bill.append("Pizza: Rs. 200\n");
                total += 200;
            }
            if (cbBurger.isChecked()) {
                bill.append("Burger: Rs. 100\n");
                total += 100;
            }
            if (cbCoffee.isChecked()) {
                bill.append("Coffee: Rs. 50\n");
                total += 50;
            }
            if (cbFries.isChecked()) {
                bill.append("French Fries: Rs. 80\n");
                total += 80;
            }

            // Append Total
            bill.append("\n----------------------\n");
            bill.append("Total Cost: Rs. ").append(total);

            // Constraint: Disable controls so user cannot change order
            cbPizza.setEnabled(false);
            cbBurger.setEnabled(false);
            cbCoffee.setEnabled(false);
            cbFries.setEnabled(false);
            btnOrder.setEnabled(false);

            // Navigate to Bill Activity
            if (total > 0) {
                Intent intent = new Intent(MainActivity.this, BillActivity.class);
                intent.putExtra("BILL_DATA", bill.toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                // Re-enable if nothing was selected
                cbPizza.setEnabled(true);
                cbBurger.setEnabled(true);
                cbCoffee.setEnabled(true);
                cbFries.setEnabled(true);
                btnOrder.setEnabled(true);
            }
        });
    }
}