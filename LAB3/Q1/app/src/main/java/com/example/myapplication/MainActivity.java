package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with the sections adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding Fragments for each Tab
        adapter.addFragment(new ListFragment(), "Artists"); // List View Tab
        adapter.addFragment(new GridFragment(), "Albums");  // Grid View Tab
        adapter.addFragment(new TableFragment(), "Songs");  // Table Layout Tab

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    // Adapter Class to Manage Fragments
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // 1. LIST FRAGMENT
    public static class ListFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_list, container, false);
            ListView listView = view.findViewById(R.id.listView);

            // Data for ListView [cite: 574]
            String[] countries = {
                    "American Samoa", "El Salvador", "Saint Helena",
                    "Saint Kitts and Nevis", "Saint Lucia",
                    "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
                    "Samoa", "San Marino", "Saudi Arabia"
            };

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, countries);
            listView.setAdapter(adapter);
            return view;
        }
    }

    // 2. GRID FRAGMENT
    public static class GridFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_grid, container, false);
            GridView gridView = view.findViewById(R.id.gridView);

            // Using dummy text data for grid to avoid missing image errors
            String[] data = {"Img 1", "Img 2", "Img 3", "Img 4", "Img 5", "Img 6", "Img 7", "Img 8", "Img 9"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
            gridView.setAdapter(adapter);
            return view;
        }
    }

    // 3. TABLE FRAGMENT
    public static class TableFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Just inflating the static table layout [cite: 563]
            return inflater.inflate(R.layout.fragment_table, container, false);
        }
    }
}