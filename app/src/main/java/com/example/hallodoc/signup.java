package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    private static final String TAG = "signup"; // Tag for logging

    EditText name, email, password;
    Button signup;
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initializing the views
        signup = findViewById(R.id.signup_button);
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_text);

        // Setting up the Firebase instance and reference
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users"); // Store user data under "users" node

        // Sign up button action
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString().trim();
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();

                // Validate input fields
                if (n.isEmpty() || e.isEmpty() || p.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                    email.setError("Invalid email address");
                    return;
                }

                if (p.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return;
                }

                // Creating a helper object to store in Firebase
                helperdatabase helper = new helperdatabase(n, e, p);
                reference.push().setValue(helper)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(signup.this, "You have signed up successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(signup.this, login.class);
                                startActivity(i);
                            } else {
                                Log.e(TAG, "Signup failed: " + task.getException().getMessage());
                                Toast.makeText(signup.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Navigate to login screen
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signup.this, login.class);
                startActivity(i);
            }
        });
    }
}
