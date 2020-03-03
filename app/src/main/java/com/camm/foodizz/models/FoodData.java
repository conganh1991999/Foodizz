package com.camm.foodizz.models;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodData {
    private String foodId;
    private String foodName;
    private String foodPrice;
    private String categoryId;
    private String foodSquareImageUri;

    private double totalScore;
    private int numOfRate;

    private String foodDetail;
    private String restaurantId;

    private ArrayList<String> foodLandscapeImageUri;
    private ArrayList<String> foodReviewId;

    private int isLiked = 0;

    // home
    public FoodData(String foodId, String foodName, String foodPrice, String categoryId, String foodSquareImageUri)
    {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.categoryId = categoryId;
        this.foodSquareImageUri = foodSquareImageUri;
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
    public void setMoreDetailData(String restaurantId, String foodDetail){
        this.restaurantId = restaurantId;
        this.foodDetail = foodDetail;
        foodLandscapeImageUri = new ArrayList<>();
        foodReviewId = new ArrayList<>();
    }

    // detail
    public void appendFoodImageUri(String imageId){
        foodLandscapeImageUri.add(imageId);
    }

    // detail
    public void appendFoodReviewId(String reviewId){
        foodReviewId.add(reviewId);
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getFoodSquareImageUri(){
        return foodSquareImageUri;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public ArrayList<String> getFoodImageUri() {
        return foodLandscapeImageUri;
    }

    public ArrayList<String> getFoodReviewId() {
        return foodReviewId;
    }

    // realtime
    public String getAverageScore(){
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(totalScore/numOfRate);
    }

}
