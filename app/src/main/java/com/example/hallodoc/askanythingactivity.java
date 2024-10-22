package com.example.hallodoc;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class askanythingactivity extends AppCompatActivity {

    private SearchView searchView;
    private TextView responseTextView;
    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        searchView = findViewById(R.id.search_view);
//        responseTextView = findViewById(R.id.response_text_view);

        // Handle search query submission
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    callOpenAIAPI(query);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid query", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

        RequestBody body = RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json")
        );

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
                runOnUiThread(() -> {
                    showErrorOnUI("Failed to connect to OpenAI API.");
                    Toast.makeText(getApplicationContext(), "Failed to connect to OpenAI API", Toast.LENGTH_SHORT).show();
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
                        runOnUiThread(() -> responseTextView.setText(result.trim()));

                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorOnUI("Error parsing the response.");
                    }
                } else {
                    runOnUiThread(() -> {
                        showErrorOnUI("Received an error from the API.");
                        Toast.makeText(getApplicationContext(), "Error in API response", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    // Method to show error messages on UI
    private void showErrorOnUI(String errorMessage) {
        runOnUiThread(() -> responseTextView.setText(errorMessage));
    }
}
