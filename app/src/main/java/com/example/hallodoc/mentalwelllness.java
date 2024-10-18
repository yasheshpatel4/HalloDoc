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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mentalwelllness extends AppCompatActivity {

    private static final int UPI_PAYMENT_REQUEST_CODE = 1;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 1;
    private final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    private Button consultNowButton, pay30Button, consultNowbutton2, pay30Button2, consultNowButton3, Pay30Button3;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentalwellnessxml);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Find buttons by ID
        consultNowButton = findViewById(R.id.consult_now_button);
        pay30Button = findViewById(R.id.more_info_button);
        consultNowbutton2 = findViewById(R.id.consult_now_button2);
        pay30Button2 = findViewById(R.id.more_info_button2);
        consultNowButton3 = findViewById(R.id.consult_now_button3);
        Pay30Button3 = findViewById(R.id.more_info_button3);

        // Back button implementation
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Check and set visibility for consult buttons
        setConsultButtonVisibility();

        // Set button click listeners
        pay30Button.setOnClickListener(v -> makeUpiPayment("Yashesh Patel", "yasheshpatel425@okhdfcbank", "Consultation", "1.00"));

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


        Pay30Button3.setOnClickListener(v -> {
            try {
                paymentcard();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        consultNowButton.setOnClickListener(v -> initiateWhatsAppVideoCall("8866330537")); // Replace with actual phone number

        consultNowbutton2.setOnClickListener(v -> initiateWhatsAppVideoCall("7984279401")); // Replace with actual phone number

        consultNowButton3.setOnClickListener(v -> initiateWhatsAppVideoCall("9824276510")); // Replace with actual phone number
    }

    private void setConsultButtonVisibility() {
        boolean isConsultButtonVisible = sharedPreferences.getBoolean("consult_button_visible", false);
        consultNowButton.setVisibility(isConsultButtonVisible ? View.VISIBLE : View.GONE);
        consultNowbutton2.setVisibility(isConsultButtonVisible ? View.VISIBLE : View.GONE);
        consultNowButton3.setVisibility(isConsultButtonVisible ? View.VISIBLE : View.GONE);
    }

    private void paymentcard() throws JSONException {
        PaymentsClient paymentsClient;
        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST) // Switch to TEST for development
                .build();

        paymentsClient = Wallet.getPaymentsClient(this, walletOptions);
        IsReadyToPayRequest readyToPayRequest = IsReadyToPayRequest.fromJson(baseconfiguration().toString());

        Task<Boolean> task = paymentsClient.isReadyToPay(readyToPayRequest);
        task.addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    showGooglePayButton(task.getResult());
                } else {
                    Toast.makeText(mentalwelllness.this, "Google Pay not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Build the payment request
        final JSONObject paymentRequestJson = baseconfiguration();
        paymentRequestJson.put("transactionInfo", new JSONObject()
                .put("totalPrice", "30")
                .put("totalPriceStatus", "FINAL")
                .put("currencyCode", "INR")
        );

        paymentRequestJson.put("merchantInfo", new JSONObject()
                .put("merchantId", "BCR2DN4TSO5P3JSS")
                .put("merchantName", "zeel")
        );

        final PaymentDataRequest request = PaymentDataRequest.fromJson(paymentRequestJson.toString());

        AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                this, LOAD_PAYMENT_DATA_REQUEST_CODE
        );
    }

    private void showGooglePayButton(boolean ready) {
        Pay30Button3.setVisibility(ready ? View.VISIBLE : View.GONE);
    }

    private static JSONObject baseconfiguration() throws JSONException {
        return new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0)
                .put("allowedPaymentMethods", new JSONArray().put(getCardPaymentMethod()));
    }

    private static JSONObject getCardPaymentMethod() throws JSONException {
        final String[] networks = new String[]{"VISA", "AMEX"};
        final String[] authMethods = new String[]{"PAN_ONLY", "CRYPTOGRAM_3DS"};

        JSONObject card = new JSONObject();
        card.put("type", "CARD");
        card.put("tokenizationSpecification", getTokenizationSpec());
        card.put("parameters", new JSONObject()
                .put("allowedAuthMethods", new JSONArray(authMethods))
                .put("allowedCardNetworks", new JSONArray(networks))
        );

        return card;
    }

    // Dummy tokenization specification
    private static JSONObject getTokenizationSpec() throws JSONException {
        return new JSONObject()
                .put("type", "PAYMENT_GATEWAY")
                .put("parameters", new JSONObject()
                        .put("gateway", "example")
                        .put("gatewayMerchantId", "BCR2DN4TSO5P3JSS"));
    }

    private void makeUpiPayment(String name, String upiId, String note, String amount) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);

        try {
            startActivityForResult(intent, UPI_PAYMENT_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Google Pay app not found. Please install it.", Toast.LENGTH_SHORT).show();
        }
    }

    // This method is called after the UPI payment process finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK || resultCode == 11) {
                if (data != null) {
                    String response = data.getStringExtra("response");
                    handleUpiPaymentResponse(response);
                } else {
                    handleUpiPaymentResponse("nothing");
                }
            } else {
                handleUpiPaymentResponse("failed");
            }
        }

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    // Payment was successful
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    if (paymentData != null) {
                        // You can extract the token/payment data if needed
                        String paymentInfo = paymentData.toJson();
                        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                        consultNowButton3.setVisibility(View.VISIBLE); // Show consult button after successful payment

                        // Save the visibility state in SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("consult_button_visible", true); // Set the flag to true
                        editor.apply();

                        // Set visibility for other consult buttons
                        consultNowButton.setVisibility(View.VISIBLE);
                        consultNowbutton2.setVisibility(View.VISIBLE);
                    }
                    break;
                case RESULT_CANCELED:
                    // Payment canceled by the user
                    Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    // An error occurred while processing the payment
                    Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void handleUpiPaymentResponse(String response) {
        if (response == null) response = "discard";

        String status = "";
        String[] responseParts = response.split("&");
        for (String part : responseParts) {
            String[] keyValue = part.split("=");
            if (keyValue.length >= 2 && "status".equals(keyValue[0])) {
                status = keyValue[1];
                break;
            }
        }

        if ("SUCCESS".equalsIgnoreCase(status)) {
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            consultNowButton.setVisibility(View.VISIBLE);
            consultNowbutton2.setVisibility(View.VISIBLE);

            // Save the visibility state in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("consult_button_visible", true); // Set the flag to true
            editor.apply();
        } else {
            Toast.makeText(this, "Payment Failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiateWhatsAppVideoCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://wa.me/" + phoneNumber + "?text=Hello"));
        startActivity(intent);
    }
}
