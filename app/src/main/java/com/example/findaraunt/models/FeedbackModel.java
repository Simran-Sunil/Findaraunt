package com.example.findaraunt.models;

public class FeedbackModel {
    String title;
    String suggestions;

    public FeedbackModel(){}

    public FeedbackModel(String title, String suggestions){
        this.title = title;
        this.suggestions = suggestions;
    }

    public String getTitle(){ return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuggestions(){ return suggestions; }
    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
}
