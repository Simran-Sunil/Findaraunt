package com.example.findaraunt.Category_List;

public class CategoryDisplayModel {
    // variables for storing our image and name.
    private String name;
    private String imgUrl;
    private String category;
    private String rating;
    private String address;
    private String phone;

    public CategoryDisplayModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public CategoryDisplayModel(String name, String imgUrl, String category, String rating, String address, String phone) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.rating = rating;
        this.address = address;
        this.phone = phone;
    }

    // getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) { this.rating = rating; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone; }
}
