package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class consultation_new extends Fragment {
    ImageView mental;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultation_new, container, false);

        // Initialize the ImageView
        mental = view.findViewById(R.id.mental_wellness_icon);

        // Set an onClickListener for the ImageView
        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the mentalwellness activity when the ImageView is clicked
                Intent i = new Intent(getContext(), mentalwelllness.class);
                startActivity(i);
            }
        });

        // Return the view that was inflated
        return view;
    }
}
