package com.example.hallodoc;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileactivity extends AppCompatActivity {

    private EditText firstName, lastName, dob, email;
    private RadioGroup genderGroup;
    private RadioButton selectedGender;
    private Spinner profileCreatedFor;
    private Button saveButton;

    // Firebase database reference
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);

        // Initialize views
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        genderGroup = findViewById(R.id.genderGroup);
        profileCreatedFor = findViewById(R.id.profileCreatedFor);
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profiles");  // Create a node called "Profiles"

        // Set save button action
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });
    }

    // Method to save profile data to Firebase
    private void saveProfileData() {
        String firstNameValue = firstName.getText().toString();
        String lastNameValue = lastName.getText().toString();
        String dobValue = dob.getText().toString();
        String emailValue = email.getText().toString();

        // Get selected gender
        int selectedId = genderGroup.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        String genderValue = selectedGender != null ? selectedGender.getText().toString() : "Not selected";

        // Get the profile created for value
        String profileCreatedForValue = profileCreatedFor.getSelectedItem().toString();

        // Generate a unique ID for each profile
        String profileId = databaseReference.push().getKey();

        // Create a Profile object
        Profile profile = new Profile(firstNameValue, lastNameValue, dobValue, genderValue, profileCreatedForValue, emailValue);

        // Store the profile in the Firebase database
        databaseReference.child(profileId).setValue(profile).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(profileactivity.this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(profileactivity.this, "Failed to save profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
