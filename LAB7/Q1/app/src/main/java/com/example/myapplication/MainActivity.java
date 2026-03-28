package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewApps;
    List<ApplicationInfo> installedApps;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewApps = findViewById(R.id.listViewApps);
        pm = getPackageManager();

        // 1. Fetch all installed apps
        installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String[] appNames = new String[installedApps.size()];

        for (int i = 0; i < installedApps.size(); i++) {
            appNames[i] = pm.getApplicationLabel(installedApps.get(i)).toString();
        }

        // 2. Display in ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appNames);
        listViewApps.setAdapter(adapter);

        // 3. IMPORTANT: Tell Android this ListView has a Long-Press Menu!
        registerForContextMenu(listViewApps);
    }

    // LEGO BRICK 2: Build the Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Find out which app was long-pressed
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        ApplicationInfo clickedApp = installedApps.get(info.position);

        // Check if it's a System App or User App
        boolean isSystemApp = (clickedApp.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        String appType = isSystemApp ? " (System App)" : " (User-Installed)";

        // Set the Title of the popup menu to show the App Type
        menu.setHeaderTitle(pm.getApplicationLabel(clickedApp) + appType);

        // Add options to the menu (groupId, itemId, order, title)
        menu.add(0, 1, 0, "Open App");
        menu.add(0, 2, 0, "Uninstall");
        menu.add(0, 3, 0, "View Details");
    }

    // LEGO BRICK 3: Handle Clicks on the Context Menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ApplicationInfo clickedApp = installedApps.get(info.position);
        String packageName = clickedApp.packageName;

        if (item.getItemId() == 1) { // Open App
            Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                Toast.makeText(this, "Cannot open this system app", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == 2) { // Uninstall
            // Prompt user for confirmation before uninstalling
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Uninstall")
                    .setMessage("Are you sure you want to uninstall this app?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + packageName));
                        startActivity(intent);
                    })
                    .setNegativeButton("No", null)
                    .show();

        } else if (item.getItemId() == 3) { // View Details
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("PACKAGE_NAME", packageName);
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }
}