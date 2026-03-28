package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {
    private String sectionName;

    // Constructor to pass data (Empty constructor is required by Android, so we overload)
    public NewsFragment() {}

    public NewsFragment(String sectionName) {
        this.sectionName = sectionName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView label = view.findViewById(R.id.sectionLabel);
        if (sectionName != null) {
            label.setText(sectionName);
        }

        return view;
    }
}