package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class appointment extends Fragment {
    ImageView v1,v2,v3,v4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_appoinment, container, false);
        v1=(ImageView) view.findViewById(R.id.pic1);
        v2=(ImageView) view.findViewById(R.id.pic2);
        v3=(ImageView) view.findViewById(R.id.pic3);
        v4=(ImageView) view.findViewById(R.id.pic4);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext() ,first.class);
                startActivity(i);
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),second.class);
                startActivity(i);
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),third.class);
                startActivity(i);
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),fourth.class);
                startActivity(i);
            }
        });
        return view;
    }
}
