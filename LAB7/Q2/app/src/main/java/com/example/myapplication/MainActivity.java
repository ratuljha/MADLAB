package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnMyMenu;
    ImageView imageViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMyMenu = findViewById(R.id.btnMyMenu);
        imageViewDisplay = findViewById(R.id.imageViewDisplay);

        // Set the standard click listener on the ImageButton
        btnMyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Create the PopupMenu anchored to the button (v)
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnMyMenu);

                // 2. Inflate the menu XML
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                // 3. Set the click listener for the menu items
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        // Make the ImageView visible once an item is clicked
                        imageViewDisplay.setVisibility(View.VISIBLE);

                        if (item.getItemId() == R.id.item_image1) {
                            imageViewDisplay.setImageResource(android.R.drawable.ic_menu_camera);
                            Toast.makeText(MainActivity.this, "Displaying Image 1: Camera", Toast.LENGTH_SHORT).show();
                            return true;

                        } else if (item.getItemId() == R.id.item_image2) {
                            imageViewDisplay.setImageResource(android.R.drawable.ic_menu_mapmode);
                            Toast.makeText(MainActivity.this, "Displaying Image 2: Map", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                });

                // 4. SHOW THE MENU! (Crucial step)
                popupMenu.show();
            }
        });
    }
}