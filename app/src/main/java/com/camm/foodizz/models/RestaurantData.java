package com.camm.foodizz.models;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RestaurantData {
    private String restaurantId;
    private String restaurantName;
    private String restaurantLogoUri;
    private String restaurantImageUri;
    private ArrayList<String> categoryId;
    private ArrayList<String> restaurantFoodId;
    private ArrayList<String> restaurantReviewId;
    private double totalScore;
    private int numOfRate;

    // home
    public RestaurantData(String restaurantId, String restaurantName, String restaurantLogoUri, ArrayList<String> categoryId) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantLogoUri = restaurantLogoUri;
        this.categoryId = categoryId;
    }

    // realtime
    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    // realtime
    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
    }

    // detail
    public void setMoreDetailData(String restaurantImageUri){
        this.restaurantImageUri = restaurantImageUri;

        restaurantFoodId = new ArrayList<>();
        restaurantReviewId = new ArrayList<>();
    }

    // detail
    public void appendFoodId(String foodId){
        restaurantFoodId.add(foodId);
    }

    // detail
    public void appendFoodReviewId(String reviewId){
        restaurantReviewId.add(reviewId);
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

    public ArrayList<String> getCategoryId() {
        return categoryId;
    }

    public ArrayList<String> getRestaurantFoodId() {
        return restaurantFoodId;
    }

    public ArrayList<String> getRestaurantReviewId() {
        return restaurantReviewId;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    // realtime
    public String getAverageScore(){
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(totalScore/numOfRate);
    }
}
