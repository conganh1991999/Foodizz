package com.camm.foodizz.models;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: change this class, remove builder
public class Food implements Serializable {

    private String foodId;
    private String foodName;
    private double foodPrice;
    private double totalScore;
    private int numOfRate;

    private String foodSquareImageUri;
    private ArrayList<String> foodLandscapeImageUri;
    private String foodDetail;
    private String restaurantId;
    private String restaurantName;

    public Food(String foodId, String foodName, double foodPrice, double totalScore, int numOfRate){
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.totalScore = totalScore;
        this.numOfRate = numOfRate;
    }

    public void setFoodSquareImageUri(String foodSquareImageUri) {
        this.foodSquareImageUri = foodSquareImageUri;
    }

    public void setFoodLandscapeImageUri(ArrayList<String> foodLandscapeImageUri) {
        this.foodLandscapeImageUri = foodLandscapeImageUri;
    }

    public void setFoodDetail(String foodDetail) {
        this.foodDetail = foodDetail;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantName(String restaurantName) {
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
