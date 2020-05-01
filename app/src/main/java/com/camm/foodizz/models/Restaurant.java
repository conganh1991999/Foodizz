package com.camm.foodizz.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    private String restaurantId;
    private String restaurantName;
    private String restaurantLogoUri;
    private String restaurantImageUri;
    private ArrayList<String> categoryName;
    private double totalScore;
    private int numOfRate;

    public Restaurant(String restaurantId, String restaurantName,
                      String restaurantLogoUri, String restaurantImageUri,
                      ArrayList<String> categoryName, double totalScore, int numOfRate)
    {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantLogoUri = restaurantLogoUri;
        this.restaurantImageUri = restaurantImageUri;
        this.categoryName = categoryName;
        this.totalScore = totalScore;
        this.numOfRate = numOfRate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantLogoUri() {
        return restaurantLogoUri;
    }

    public String getRestaurantImageUri() {
        return restaurantImageUri;
    }

    public ArrayList<String> getCategoryName() {
        return categoryName;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public double getAverageScore(){
        return totalScore/numOfRate;
    }
}
