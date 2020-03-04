package com.camm.foodizz.models.pushdata;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PushRestaurantData {

    public void pushRestaurant(ArrayList<String> categoryName, String image, String logo, String name, double totalScore){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("restaurants");
        String key = databaseRef.push().getKey();
        if(key != null){
            databaseRef.child(key).child("categoryName").setValue(categoryName);
            databaseRef.child(key).child("image").setValue(image);
            databaseRef.child(key).child("logo").setValue(logo);
            databaseRef.child(key).child("name").setValue(name);
            databaseRef.child(key).child("numOfRate").setValue(1);
            databaseRef.child(key).child("totalScore").setValue(totalScore);
        }
    }
}
