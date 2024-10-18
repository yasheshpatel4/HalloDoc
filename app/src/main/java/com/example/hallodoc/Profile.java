package com.example.hallodoc;

public class Profile {

    private String firstName;
    private String lastName;
    private String dob;
    private String gender;
    private String profileCreatedFor;
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    public Profile() {
    }

    public Profile(String firstName, String lastName, String dob, String gender, String profileCreatedFor, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.profileCreatedFor = profileCreatedFor;
        this.email = email;
    }

    // Getters and setters for each field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileCreatedFor() {
        return profileCreatedFor;
    }

    public void setProfileCreatedFor(String profileCreatedFor) {
        this.profileCreatedFor = profileCreatedFor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
