package com.example.hallodoc;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class home extends Fragment {

    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    String[] dentalInfo = {
            "Brush your teeth twice daily Use fluoride toothpaste Floss regularly to remove food particles Visit your dentist every 6 months Avoid sugary snacks and drinks"
    };

    String[] skinCareInfo = {
            "Cleanse your face twice daily Use sunscreen with SPF 30+ Moisturize daily Drink plenty of water Exfoliate weekly Avoid prolonged sun exposure"
    };

    String[] eyeCareInfo = {
            "Get regular eye check-ups Take breaks from screens Wear sunglasses for UV protection Keep your eyes hydrated Maintain a diet rich in Vitamin A Use proper lighting when reading"
    };

    private SearchView searchView;
    private TextView responseTextView;
    private ImageView appoinment_image, consult_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ListViews and set them to GONE initially
        ListView d = view.findViewById(R.id.dental_id);
        d.setVisibility(View.GONE);
        ListView s = view.findViewById(R.id.skincare_id);
        s.setVisibility(View.GONE);
        ListView e = view.findViewById(R.id.eye_id);
        e.setVisibility(View.GONE);

        // Initialize the SearchView and TextView for responses
        searchView = view.findViewById(R.id.search_view);
//        responseTextView = view.findViewById(R.id.response_text_view);
        appoinment_image = view.findViewById(R.id.book_image);
        consult_image = view.findViewById(R.id.consult_image);

        // Set Adapters for ListViews
        ArrayAdapter<String> dental = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dentalInfo);
        ArrayAdapter<String> skincare = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, skinCareInfo);
        ArrayAdapter<String> eye = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, eyeCareInfo);

        d.setAdapter(dental);
        s.setAdapter(skincare);
        e.setAdapter(eye);

        // Toggle visibility of ListViews based on the layout click
        LinearLayout dental_layout = view.findViewById(R.id.dental_layout);
        dental_layout.setOnClickListener(v -> {
            if (d.getVisibility() == View.GONE) {
                d.setVisibility(View.VISIBLE);
                s.setVisibility(View.GONE); // Hide skincare info
                e.setVisibility(View.GONE); // Hide eye info
            } else {
                d.setVisibility(View.GONE);
            }
        });

        LinearLayout skincare_layout = view.findViewById(R.id.skincare_layout);
        skincare_layout.setOnClickListener(v -> {
            if (s.getVisibility() == View.GONE) {
                s.setVisibility(View.VISIBLE);
                d.setVisibility(View.GONE); // Hide dental info
                e.setVisibility(View.GONE); // Hide eye info
            } else {
                s.setVisibility(View.GONE);
            }
        });

        LinearLayout eye_layout = view.findViewById(R.id.eye_layout);
        eye_layout.setOnClickListener(v -> {
            if (e.getVisibility() == View.GONE) {
                e.setVisibility(View.VISIBLE);
                d.setVisibility(View.GONE); // Hide dental info
                s.setVisibility(View.GONE); // Hide skincare info
            } else {
                e.setVisibility(View.GONE);
            }
        });

        appoinment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        consult_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Log.d("API Key", OPENAI_API_KEY);  // To verify the API key is correct


        // Handle search query submission for OpenAI
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    callOpenAIAPI(query);
                } else {
                    Toast.makeText(getContext(), "Please enter a valid query", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void callOpenAIAPI(String query) {
        OkHttpClient client = new OkHttpClient();

        // Create the JSON request body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "text-davinci-003");
            jsonObject.put("prompt", query);
            jsonObject.put("max_tokens", 150);
            jsonObject.put("temperature", 0.7);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorOnUI("Failed to create JSON request.");
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));

        // Create the request
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .post(body)
                .build();

        // Make the API call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    showErrorOnUI("Failed to connect to OpenAI API.");
                    Toast.makeText(getContext(), "Failed to connect to OpenAI API", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonData = response.body().string();
                        JSONObject jsonResponse = new JSONObject(jsonData);
                        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
                        String result = choicesArray.getJSONObject(0).getString("text");

                        // Update the response on UI thread
                        getActivity().runOnUiThread(() -> responseTextView.setText(result.trim()));

                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorOnUI("Error parsing the response.");
                    }
                } else {
                    getActivity().runOnUiThread(() -> {
                        showErrorOnUI("Received an error from the API.");
                        Toast.makeText(getContext(), "Error in API response", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void showErrorOnUI(String errorMessage) {
        getActivity().runOnUiThread(() -> responseTextView.setText(errorMessage));
    }


}
