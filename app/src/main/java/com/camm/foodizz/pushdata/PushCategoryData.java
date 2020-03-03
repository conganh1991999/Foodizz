package com.camm.foodizz.pushdata;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PushCategoryData {

    private static int PRIORITY = 0;

    public void pushCategory(String name, String image){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("categories");
        String key = databaseRef.push().getKey();
        if(key != null){
            databaseRef.child(key).child("priority").setValue(PRIORITY);
            databaseRef.child(key).child("name").setValue(name);
            databaseRef.child(key).child("image").setValue(image);
            PRIORITY += 1;
        }
    }

}
