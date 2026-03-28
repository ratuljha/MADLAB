package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText etKeyword;
    TextView tvContent;

    // The default paragraph about Digital Transformation
    String originalContent = "Digital transformation is the integration of digital technology into all areas of a business. It fundamentally changes how you operate and deliver value to customers. It is also a cultural change that requires organizations to continually challenge the status quo. Businesses must adapt to stay relevant in the modern world.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etKeyword = findViewById(R.id.etKeyword);
        tvContent = findViewById(R.id.tvContent);

        // Load the initial content
        tvContent.setText(originalContent);
    }

    // Load the Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    // Handle Submenu Clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String keyword = etKeyword.getText().toString().trim().toLowerCase();
        String currentText = originalContent.toLowerCase();

        if (item.getItemId() == R.id.menu_search) {
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Please enter a keyword first", Toast.LENGTH_SHORT).show();
                return true;
            }

            // Search logic: Check if the paragraph contains the word
            if (currentText.contains(keyword)) {
                Toast.makeText(this, "Keyword Found!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Keyword Not Found.", Toast.LENGTH_SHORT).show();
            }
            return true;

        } else if (item.getItemId() == R.id.menu_highlight) {
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Please enter a keyword to highlight", Toast.LENGTH_SHORT).show();
                return true;
            }

            // Highlight logic using SpannableString
            SpannableString spannableString = new SpannableString(originalContent);
            int startIndex = currentText.indexOf(keyword);

            // Loop to highlight all occurrences of the word
            while (startIndex >= 0) {
                spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), startIndex, startIndex + keyword.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex = currentText.indexOf(keyword, startIndex + keyword.length());
            }

            tvContent.setText(spannableString);
            return true;

        } else if (item.getItemId() == R.id.menu_sort) {
            // Sort logic: Split the paragraph into sentences, sort them alphabetically, and rebuild
            String[] sentences = originalContent.split("\\. ");
            Arrays.sort(sentences);

            StringBuilder sortedContent = new StringBuilder();
            for (String sentence : sentences) {
                sortedContent.append(sentence.trim()).append(".\n\n");
            }

            tvContent.setText(sortedContent.toString());
            Toast.makeText(this, "Content Sorted Alphabetically", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}