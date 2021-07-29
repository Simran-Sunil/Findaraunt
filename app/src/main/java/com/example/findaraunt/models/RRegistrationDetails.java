package com.example.findaraunt.models;

public class RRegistrationDetails {
    private String name;
    private String category;
    private String address;
    private String phone;
    private String timing;
    private String rating;
    private String imgUrl;

    // Blank constructor
    public RRegistrationDetails(){

    }

    // Constructor to initialize all values
    public RRegistrationDetails(String name, String category, String address, String phone, String timing, String rating, String imgUrl) {

        this.name = name;
        this.category = category;
        this.address = address;
        this.phone = phone;
        this.timing = timing;
        this.rating = rating;
        this.imgUrl = imgUrl;
    }

    public String getName(){ return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory(){ return category; }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress(){ return address; }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone(){ return phone; }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTiming(){ return timing; }
    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getRating(){ return rating; }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getImgUrl(){ return imgUrl; }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
