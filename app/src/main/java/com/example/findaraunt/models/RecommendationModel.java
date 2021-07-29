package com.example.findaraunt.models;

public class RecommendationModel {
    // variables for storing our image and name.
    private String name;
    private String imgUrl;
    private String hcategory;
    private String rRating;

    // constructor for our object class.
    public RecommendationModel(String name, String imgUrl, String hcategory, String rRating) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.hcategory = hcategory;
        this.rRating = rRating;
    }

    public RecommendationModel(){};

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

    public String getrRating(){ return rRating; }

    public void setrRating(String rRating){ this.rRating = rRating; }
}

