package com.example.hallodoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hallodoc.fragment.DatePickerFragment;
import com.example.hallodoc.fragment.TimePickerFragment;
import com.google.android.material.snackbar.Snackbar;

public class dental extends AppCompatActivity {
    Button b1,b2,b3,b4;
    EditText t,tt;
    String selectedTime;
    String selectedDate;
    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dental);
        b1=(Button)findViewById(R.id.email);
        b2=(Button)findViewById(R.id.call);
        b3=(Button)findViewById(R.id.button2);
        b4=(Button)findViewById(R.id.button3);
        t=(EditText) findViewById(R.id.e1);
        tt=(EditText) findViewById(R.id.e2);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment t1=new TimePickerFragment(t);
                t1.show(getSupportFragmentManager(),"Time picker");

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment t1=new DatePickerFragment(tt);
                t1.show(getSupportFragmentManager(),"Date Picker");

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate=tt.getText().toString();
                selectedTime=t.getText().toString();
                String emailBody = "Hello, I would like to inquire about an appointment at " + selectedTime + " on date "+selectedDate;
                sendEmail("yasheshpatel425@gmail.com", "Appointment Inquiry", emailBody);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber();
            }
        });

    }
    private void sendEmail(String recipient, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }
    private void dialPhoneNumber() {
        String phoneNumber = "+918866330537";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}