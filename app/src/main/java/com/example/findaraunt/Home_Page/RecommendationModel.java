package com.example.findaraunt.Home_Page;

public class RecommendationModel {
    // variables for storing our image and name.
    private String name;
    private String imgUrl;
    private String hcategory;

    public RecommendationModel() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public RecommendationModel(String name, String imgUrl, String hcategory) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.hcategory = hcategory;
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

    public String getHcategory() {
        return hcategory;
    }

    public void setHcategory(String hcategory) {
        this.hcategory = hcategory;
    }
}

