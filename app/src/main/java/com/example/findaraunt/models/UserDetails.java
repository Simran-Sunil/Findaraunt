package com.example.findaraunt.models;

// Modal class to save the details in registration page
public class UserDetails {
    private String username,email,password,location, phoneno;
    String profileImg;

    // Blank constructor
    public UserDetails(){

    }

    // Constructor to initialize all values
    public UserDetails(String username, String email, String password, String location, String phoneno) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.location = location;
        this.phoneno = phoneno;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno){
        this.phoneno = phoneno;
    }
}
