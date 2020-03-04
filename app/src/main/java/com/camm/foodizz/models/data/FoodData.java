package com.camm.foodizz.models.data;

import java.util.ArrayList;

public class FoodData {
    private String foodId;
    private String foodName;
    private double foodPrice;
    private String foodSquareImageUri;
    private ArrayList<String> foodLandscapeImageUri;
    private String foodDetail;
    private String restaurantId;
    private String restaurantName;

    private double totalScore;
    private int numOfRate;

    // home
    public FoodData(String foodId, String foodName, double foodPrice, String foodSquareImageUri,
                    double totalScore, int numOfRate
    )
    {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodSquareImageUri = foodSquareImageUri;
        this.totalScore = totalScore;
        this.numOfRate = numOfRate;
    }

    // detail
    public FoodData(String foodId, String foodName, double foodPrice, ArrayList<String> foodLandscapeImageUri,
                    String foodDetail, String restaurantId, String restaurantName
    ) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodLandscapeImageUri = foodLandscapeImageUri;
        this.foodDetail = foodDetail;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public String getFoodSquareImageUri(){
        return foodSquareImageUri;
    }

    public ArrayList<String> getFoodLandscapeImageUri() {
        return foodLandscapeImageUri;
    }

    public String getFoodDetail() {
        return foodDetail;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getAverageScore(){
        return totalScore/numOfRate;
    }

}
