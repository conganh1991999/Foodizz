package com.camm.foodizz.pushdata;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PushRestaurantData {

    public void pushRestaurant(ArrayList<String> categoryId, ArrayList<String> foodId, String image, String name, String logo){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("restaurants");
        String key = databaseRef.push().getKey();
        if(key != null){
            databaseRef.child(key).child("id").setValue(key);
            databaseRef.child(key).child("categoryId").setValue(categoryId);
            databaseRef.child(key).child("image").setValue(image);
            databaseRef.child(key).child("logo").setValue(logo);
            databaseRef.child(key).child("name").setValue(name);
            databaseRef.child(key).child("foodId").setValue(foodId);
            databaseRef.child(key).child("numOfRate").setValue(0);
            databaseRef.child(key).child("totalScore").setValue(0.0);
        }
    }
}
