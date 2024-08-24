package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText enter_number;
    Button getotp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner countryCodeSpinner = findViewById(R.id.countryCodeSpinner);

        // Find the position of "+91" in the array
        String[] countryCodes = getResources().getStringArray(R.array.country_codes);
        int defaultPosition = 0;
        for (int i = 0; i < countryCodes.length; i++) {
            if (countryCodes[i].equals("+91")) {
                defaultPosition = i;
                break;
            }
        }
        // Set the default selection
        countryCodeSpinner.setSelection(defaultPosition);

        enter_number = (EditText) findViewById(R.id.mobileNumberEditText);
        getotp = (Button) findViewById(R.id.continueButton);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enter_number.getText().toString().trim().isEmpty()){
                    if(enter_number.getText().toString().trim().length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        getotp.setVisibility(View.GONE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enter_number.getText().toString(), 30, TimeUnit.SECONDS, MainActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotp.setVisibility(View.VISIBLE);

                                        Intent i = new Intent(MainActivity.this,otpverification.class);
                                        i.putExtra("mobile",enter_number.getText().toString());
                                        i.putExtra("backendotp",s);
                                        startActivity(i);
                                    }
                                }
                        );
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"please enter correct number",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"enter mobile number",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}