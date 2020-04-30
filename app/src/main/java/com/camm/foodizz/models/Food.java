package com.camm.foodizz.models;

import java.util.ArrayList;

public class Food {

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

    private Food(FoodBuilder builder) {
        this.foodId = builder.foodId;
        this.foodName = builder.foodName;
        this.foodPrice = builder.foodPrice;
        this.foodSquareImageUri = builder.foodSquareImageUri;
        this.foodLandscapeImageUri = builder.foodLandscapeImageUri;
        this.foodDetail = builder.foodDetail;
        this.restaurantId = builder.restaurantId;
        this.restaurantName = builder.restaurantName;
        this.totalScore = builder.totalScore;
        this.numOfRate = builder.numOfRate;
    }

    public static class FoodBuilder{
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

        public FoodBuilder(String foodId, String foodName, double foodPrice, double totalScore, int numOfRate){
            this.foodId = foodId;
            this.foodName = foodName;
            this.foodPrice = foodPrice;
            this.totalScore = totalScore;
            this.numOfRate = numOfRate;
        }

        public FoodBuilder setFoodSquareImageUri(String foodSquareImageUri){
            this.foodSquareImageUri = foodSquareImageUri;
            return this;
        }

        public FoodBuilder setFoodLandscapeImageUri(ArrayList<String> foodLandscapeImageUri){
            this.foodLandscapeImageUri = foodLandscapeImageUri;
            return this;
        }

        public FoodBuilder setFoodDetail(String foodDetail){
            this.foodDetail = foodDetail;
            return this;
        }

        public FoodBuilder setRestaurantId(String restaurantId){
            this.restaurantId = restaurantId;
            return this;
        }

        public FoodBuilder setRestaurantName(String restaurantName){
            this.restaurantName = restaurantName;
            return this;
        }

        public Food build(){
            return new Food(this);
        }
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
