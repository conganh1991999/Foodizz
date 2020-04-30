package com.camm.foodizz.models;

public class Category {
    private String categoryName;
    private String categoryImageUri;

    public Category(String categoryName, String categoryImageUri) {
        this.categoryName = categoryName;
        this.categoryImageUri = categoryImageUri;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImageUri() {
        return categoryImageUri;
    }

}
