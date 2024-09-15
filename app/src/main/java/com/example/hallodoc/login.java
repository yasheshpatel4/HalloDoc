package com.example.hallodoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {

    TextView signup;
    EditText name, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_button);
        signup = findViewById(R.id.login_text);
        name = findViewById(R.id.username);
        password = findViewById(R.id.password);

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null){
            startActivity(new Intent(login.this,dashboard.class));
        }

        // Login button listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatename() || !validatepassword()) {
                    return;
                } else {
                    checkuser();
                }
            }
        });

        // Signup button listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, signup.class);
                startActivity(i);
            }
        });
    }

    // Validation for username
    public boolean validatename() {
        String val = name.getText().toString().trim();
        if (val.isEmpty()) {
            name.setError("Username cannot be empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    // Validation for password
    public boolean validatepassword() {
        String val = password.getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    // Check if the user exists in the database
    public void checkuser() {
        String n = name.getText().toString().trim().toLowerCase(); // Trim and convert to lowercase
        String p = password.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuserdatabase = reference.orderByChild("name").equalTo(n);

        checkuserdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name.setError(null);

                    // Loop through the results since `orderByChild` returns a list
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passworddb = userSnapshot.child("password").getValue(String.class);

                        // Check if the password matches
                        if (Objects.equals(passworddb, p)) {
                            name.setError(null);
                            Intent i = new Intent(login.this, dashboard.class);
                            startActivity(i);
                            finish();  // End login activity
                        } else {
                            password.setError("Invalid credentials");
                            password.requestFocus();
                        }
                    }
                } else {
                    name.setError("User does not exist");
                    name.requestFocus();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
                Toast.makeText(login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
