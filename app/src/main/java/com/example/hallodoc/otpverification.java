package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpverification extends AppCompatActivity {

    TextView mobile_number;
    EditText i1, i2, i3, i4, i5, i6;
    String getotpbackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

        i1 = (EditText) findViewById(R.id.input1);
        i2 = (EditText) findViewById(R.id.input2);
        i3 = (EditText) findViewById(R.id.input3);
        i4 = (EditText) findViewById(R.id.input4);
        i5 = (EditText) findViewById(R.id.input5);
        i6 = (EditText) findViewById(R.id.input6);
        mobile_number = (TextView) findViewById(R.id.mobileNumber);
        final Button verify = (Button) findViewById(R.id.continueButton);
        final ProgressBar pb = findViewById(R.id.progressBar);

        mobile_number.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));
        getotpbackend = getIntent().getStringExtra("backendotp");

        verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!i1.getText().toString().trim().isEmpty() && !i2.getText().toString().trim().isEmpty() && !i3.getText().toString().trim().isEmpty() && !i4.getText().toString().trim().isEmpty() && !i5.getText().toString().trim().isEmpty() && !i6.getText().toString().trim().isEmpty()) {

                    String entercodeotp = i1.getText().toString() +
                            i2.getText().toString() +
                            i3.getText().toString() +
                            i4.getText().toString() +
                            i5.getText().toString() +
                            i6.getText().toString();

                    if(getotpbackend!=null){
                        pb.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential pac = PhoneAuthProvider.getCredential(getotpbackend,entercodeotp);

                        FirebaseAuth.getInstance().signInWithCredential(pac).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(View.GONE);
                                verify.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    Intent i = new Intent(getApplicationContext(),dashboard.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(otpverification.this,"enter the correct otp",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), "please check your internet", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(), "otp verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numbertomove();
        findViewById(R.id.resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mobile_number.getText().toString(), 30, TimeUnit.SECONDS, otpverification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(otpverification.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getotpbackend = s;
                                Toast.makeText(otpverification.this,"OTP send successfully",Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });
    }

    private void numbertomove() {
        i1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i22) {
                if (!charSequence.toString().trim().isEmpty()) {
                    i2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        i2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i22) {
                if (!charSequence.toString().trim().isEmpty()) {
                    i3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        i3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i22) {
                if (!charSequence.toString().trim().isEmpty()) {
                    i4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        i4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i22) {
                if (!charSequence.toString().trim().isEmpty()) {
                    i5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        i5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i22) {
                if (!charSequence.toString().trim().isEmpty()) {
                    i6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
