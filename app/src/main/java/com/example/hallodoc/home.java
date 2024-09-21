package com.example.hallodoc;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    String[] dentalInfo = {
            "Brush your teeth twice daily Use fluoride toothpaste Floss regularly to remove food particles Visit your dentist every 6 months Avoid sugary snacks and drinks"
    };

    String[] skinCareInfo = {
            "Cleanse your face twice daily Use sunscreen with SPF 30+ Moisturize daily Drink plenty of water Exfoliate weekly Avoid prolonged sun exposure"
    };

    String[] eyeCareInfo = {
            "Get regular eye check-ups Take breaks from screens Wear sunglasses for UV protection Keep your eyes hydrated Maintain a diet rich in Vitamin A Use proper lighting when reading"
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView d = view.findViewById(R.id.dental_id);
        d.setVisibility(View.GONE);

        ListView s = view.findViewById(R.id.skincare_id);
        s.setVisibility(View.GONE);

        ListView e = view.findViewById(R.id.eye_id);
        e.setVisibility(View.GONE);

        ImageView appointmnet = view.findViewById(R.id.book_image);
        ImageView consult = view.findViewById(R.id.consult_image);

        ArrayAdapter<String> dental = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, dentalInfo);
        ArrayAdapter<String> skincare = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, skinCareInfo);
        ArrayAdapter<String> eye  = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, eyeCareInfo);

        d.setAdapter(dental);
        s.setAdapter(skincare);
        e.setAdapter(eye);

        LinearLayout dental_layout = (LinearLayout) view.findViewById(R.id.dental_layout);
        dental_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d.getVisibility() == View.GONE) {
                    d.setVisibility(View.VISIBLE);
                    s.setVisibility(View.GONE); // Hide skincare info
                    e.setVisibility(View.GONE); // Hide eye info
                } else {
                    d.setVisibility(View.GONE);
                }
            }
        });

        LinearLayout skincare_layout = (LinearLayout) view.findViewById(R.id.skincare_layout);
        skincare_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.getVisibility() == View.GONE) {
                    s.setVisibility(View.VISIBLE);
                    d.setVisibility(View.GONE); // Hide dental info
                    e.setVisibility(View.GONE); // Hide eye info
                } else {
                    s.setVisibility(View.GONE);
                }
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout eye_layout = (LinearLayout) view.findViewById(R.id.eye_layout);
        eye_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e.getVisibility() == View.GONE) {
                    e.setVisibility(View.VISIBLE);
                    d.setVisibility(View.GONE); // Hide dental info
                    s.setVisibility(View.GONE); // Hide skincare info
                } else {
                    e.setVisibility(View.GONE);
                }
            }
        });





        // Inflate the layout for this fragment
        return view;
    }
}