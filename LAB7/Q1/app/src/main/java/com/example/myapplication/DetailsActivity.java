package com.example.myapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView tvAppDetails = findViewById(R.id.tvAppDetails);
        String packageName = getIntent().getStringExtra("PACKAGE_NAME");
        PackageManager pm = getPackageManager();

        try {
            // Get detailed info including permissions
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);

            String appName = pm.getApplicationLabel(appInfo).toString();
            String version = packageInfo.versionName;

            // Calculate approximate size in MB
            long sizeInBytes = new File(appInfo.sourceDir).length();
            long sizeInMb = sizeInBytes / (1024 * 1024);

            // Check for special permissions
            StringBuilder specialPerms = new StringBuilder();
            if (packageInfo.requestedPermissions != null) {
                for (String perm : packageInfo.requestedPermissions) {
                    if (perm.contains("LOCATION")) specialPerms.append("- Location Access\n");
                    if (perm.contains("CAMERA")) specialPerms.append("- Camera Access\n");
                }
            }
            if (specialPerms.length() == 0) specialPerms.append("None");

            // Build the final display string
            String finalDetails = "App Name: " + appName + "\n\n" +
                    "Version: " + version + "\n\n" +
                    "Approx Size: " + sizeInMb + " MB\n\n" +
                    "Special Permissions:\n" + specialPerms.toString();

            tvAppDetails.setText(finalDetails);

        } catch (PackageManager.NameNotFoundException e) {
            tvAppDetails.setText("Error loading app details.");
        }
    }
}