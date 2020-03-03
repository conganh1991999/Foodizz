package com.camm.foodizz.pushdata;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PushFoodData {

    public void pushFood(String categoryId, String detail, ArrayList<String> landscapeImage, String name, double price,
                        String restaurantId, String squareImage)
    {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("foods");
        String key = databaseRef.push().getKey();
        if(key != null){
            databaseRef.child(key).child("id").setValue(key);
            databaseRef.child(key).child("categoryId").setValue(categoryId);
            databaseRef.child(key).child("detail").setValue(detail);
            databaseRef.child(key).child("landscapeImage").setValue(landscapeImage);
            databaseRef.child(key).child("name").setValue(name);
            databaseRef.child(key).child("price").setValue(price);
            databaseRef.child(key).child("restaurantId").setValue(restaurantId);
            databaseRef.child(key).child("squareImage").setValue(squareImage);
            databaseRef.child(key).child("numOfRate").setValue(0);
            databaseRef.child(key).child("totalScore").setValue(0.0);
        }
    }
}
