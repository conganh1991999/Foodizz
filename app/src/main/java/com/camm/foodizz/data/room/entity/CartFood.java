package com.camm.foodizz.data.room.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartFood {

    @PrimaryKey @NonNull private String foodId;
    private String foodName;
    private String foodImage;
    private double foodPrice;
    private double totalScore;
    private int quantity;

    public CartFood(String foodId, String foodName, String foodImage, double foodPrice, double totalScore, int quantity) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.totalScore = totalScore;
        this.quantity = quantity;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public int getQuantity() {
        return quantity;
    }

}
