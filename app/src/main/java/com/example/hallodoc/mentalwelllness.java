package com.example.hallodoc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class mentalwelllness extends AppCompatActivity {

    private static final int UPI_PAYMENT_REQUEST_CODE = 1;
    private Button consultNowButton, pay30Button, consultNowbutton2, pay30Button2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentalwellnessxml);

        consultNowButton = findViewById(R.id.consult_now_button);
        pay30Button = findViewById(R.id.more_info_button);

        consultNowbutton2 = findViewById(R.id.consult_now_button2);
        pay30Button2 = findViewById(R.id.more_info_button2);

        sharedPreferences = getSharedPreferences("hallodoc_prefs", MODE_PRIVATE);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back when the back button is pressed
                onBackPressed();
            }
        });

        // Load visibility states from SharedPreferences
        boolean isConsultVisible = sharedPreferences.getBoolean("consult_button_visible", false);
        boolean isConsult2Visible = sharedPreferences.getBoolean("consult_button2_visible", false);

        // Set visibility based on SharedPreferences
        consultNowButton.setVisibility(isConsultVisible ? View.VISIBLE : View.GONE);
        consultNowbutton2.setVisibility(isConsult2Visible ? View.VISIBLE : View.GONE);

        pay30Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger UPI payment
                makeUpiPayment();
            }
        });

        pay30Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger showing consultNowbutton2
                consultNowbutton2.setVisibility(View.VISIBLE);
                // Save the state of consultNowbutton2
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("consult_button2_visible", true);
                editor.apply();
            }
        });

        consultNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateWhatsAppVideoCall("8866330537");  // Replace with the doctor's phone number or meeting link
            }
        });

        consultNowbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateWhatsAppVideoCall("9824276510");  // Replace with the doctor's phone number or meeting link
            }
        });
    }

    private void makeUpiPayment() {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "zeelp4797@okhdfcbank") // UPI ID
                .appendQueryParameter("pn", "Zeel Patel") // Payee name
                .appendQueryParameter("mc", "") // Merchant code (optional)
                .appendQueryParameter("tid", "BCR2DN4TSO5P3JSS") // Transaction ID (optional)
                .appendQueryParameter("tr", "txn12345") // Transaction reference ID
                .appendQueryParameter("tn", "Consultation Payment") // Payment note
                .appendQueryParameter("am", "30") // Amount
                .appendQueryParameter("cu", "INR") // Currency
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage("com.google.android.apps.nbu.paisa.user"); // Google Pay package name

        try {
            startActivityForResult(intent, UPI_PAYMENT_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mentalwelllness.this, "Google Pay not found on this device", Toast.LENGTH_SHORT).show();
        }
    }

    // This method is called after the UPI payment process finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK || resultCode == 11) {
                // Payment was successful
                String response = data.getStringExtra("response");
                handleUpiPaymentResponse(response);
            } else {
                // Payment failed or was cancelled
                Toast.makeText(this, "Payment Failed or Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleUpiPaymentResponse(String response) {
        if (response != null && response.toLowerCase().contains("success")) {
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            consultNowButton.setVisibility(View.VISIBLE); // Show consult button after successful payment

            // Save the visibility state in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("consult_button_visible", true);
            editor.apply();
        } else {
            Toast.makeText(this, "Payment Failed or Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiateWhatsAppVideoCall(String phoneNumber) {
        // Open WhatsApp chat for the specified phone number
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mentalwelllness.this, "WhatsApp not found on this device", Toast.LENGTH_SHORT).show();
        }
    }
}
