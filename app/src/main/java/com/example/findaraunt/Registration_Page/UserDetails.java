package com.example.findaraunt.Registration_Page;

// Modal class to save the details in registration page
public class UserDetails {
    private String username,email,password,location;

    // Blank constructor
    public UserDetails(){

    }

    // Constructor to initialize all values
    public UserDetails(String username, String email, String password, String location) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }
}
