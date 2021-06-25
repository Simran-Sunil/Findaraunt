package com.example.findaraunt;

class CategoryModel {
    Integer categoryLogo;
    String categoryName;

    public CategoryModel(Integer categoryLogo, String categoryName){
        this.categoryLogo = categoryLogo;
        this.categoryName = categoryName;
    }

    public Integer getCategoryLogo(){
        return categoryLogo;
    }

    public String getCategoryName(){
        return categoryName;
    }
}
