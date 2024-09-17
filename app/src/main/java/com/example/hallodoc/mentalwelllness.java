package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 1;
    private Button consultNowButton, pay30Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentalwellnessxml);

        consultNowButton = findViewById(R.id.consult_now_button);
        pay30Button = findViewById(R.id.more_info_button);

        consultNowButton.setVisibility(View.GONE);  // Initially hide consult button

        pay30Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger payment process
                try {
                    processPayment();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void processPayment() throws JSONException {
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

    // This method is called after the payment process finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    // Payment was successful
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    if (paymentData != null) {
                        // You can extract the token/payment data if needed
                        String paymentInfo = paymentData.toJson();
                        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                        consultNowButton.setVisibility(View.VISIBLE); // Show consult button after successful payment
                    }
                    break;
                case RESULT_CANCELED:
                    // Payment canceled by the user
                    Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    // Handle any error that occurred during payment
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    // Do nothing
                    break;
            }
        }
    }

    private void showGooglePayButton(boolean ready) {
        if (ready) {
            pay30Button.setVisibility(View.VISIBLE);
        } else {
            pay30Button.setVisibility(View.GONE);
        }
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
                        .put("gatewayMerchantId", "BCR2DN4TSO5P3JSS")
                );
    }
}
