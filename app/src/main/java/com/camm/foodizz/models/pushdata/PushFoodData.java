package com.camm.foodizz.models.pushdata;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PushFoodData {

    public void pushFood(String categoryName, String detail, ArrayList<String> landscapeImage, String name, double price,
                        String restaurantId, String restaurantName, String squareImage, double totalScore)
    {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("foods");
        String key = databaseRef.push().getKey();
        if(key != null){
            databaseRef.child(key).child("categoryName").setValue(categoryName);
            databaseRef.child(key).child("detail").setValue(detail);
            databaseRef.child(key).child("landscapeImage").setValue(landscapeImage);
            databaseRef.child(key).child("name").setValue(name);
            databaseRef.child(key).child("price").setValue(price);
            databaseRef.child(key).child("restaurantId").setValue(restaurantId);
            databaseRef.child(key).child("restaurantName").setValue(restaurantName);
            databaseRef.child(key).child("squareImage").setValue(squareImage);
            databaseRef.child(key).child("numOfRate").setValue(1);
            databaseRef.child(key).child("totalScore").setValue(totalScore);
        }

        databaseRef = FirebaseDatabase.getInstance().getReference("restaurants");
        databaseRef.child(restaurantId).child("foodId").push().setValue(key);

    }
}

//        PushFoodData foodData = new PushFoodData();
//
//        ArrayList<String> landscapeImage1 = new ArrayList<>();
//        landscapeImage1.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl1_black_berry_cake.jpg?alt=media&token=81a98170-fb96-4644-9ff6-0a8727251419");
//        landscapeImage1.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl2_black_berry_cake.jpg?alt=media&token=fb1ee453-6f7e-452e-9e62-fbea9f24a875");
//        landscapeImage1.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl3_black_berry_cake.jpg?alt=media&token=82ffcb52-325b-4f19-872d-f77c87c91de3");
//
//        ArrayList<String> landscapeImage2 = new ArrayList<>();
//        landscapeImage2.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl1_grape_cake.jpg?alt=media&token=906993c6-0d89-449b-8b55-da0df49c8d4c");
//        landscapeImage2.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl2_grape_cake.jpg?alt=media&token=2e72d3ee-748c-435d-836c-eac5f69759ea");
//        landscapeImage2.add("https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fl3_grape_cake.jpg?alt=media&token=dfcb111b-62e3-48d6-b90d-c7a610d2f523");
//
//        double totalScore = 5.0;
//        for(int i = 0; i < 6; i++){
//            for(int j = 0; j < 3; j++){
//                foodData.pushFood(
//                        "Cake",
//                        "If you are hungry for success, food content marketing could be " +
//                                "the answer. Food puns are as irresistible as mom's apple pie. There's no denying that good " +
//                                "food-related content has the potential draw in the crowds. If you are hungry for success, " +
//                                "food content marketing could be the answer.",
//                        landscapeImage1,
//                        "Blackberry Cake",
//                        91.50,
//                        "-M1YlgsLe8QsqiXuIBdX",
//                        "bakery",
//                        "https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fs_black_berry_cake.jpg?alt=media&token=4d299c35-0eef-401a-aa3f-bf889e3188fb",
//                        totalScore
//                );
//
//                foodData.pushFood(
//                        "Cake",
//                        "If you are hungry for success, food content marketing could be " +
//                                "the answer. Food puns are as irresistible as mom's apple pie. There's no denying that good " +
//                                "food-related content has the potential draw in the crowds. If you are hungry for success, " +
//                                "food content marketing could be the answer.",
//                        landscapeImage2,
//                        "Grape Cake",
//                        61.50,
//                        "-M1YlgsLe8QsqiXuIBdX",
//                        "bakery",
//                        "https://firebasestorage.googleapis.com/v0/b/foodsorder-a8ca4.appspot.com/o/foods%2Fs_grape_cake.jpg?alt=media&token=33bb04e4-d95c-45f6-a262-43c3f84ab904",
//                        totalScore
//                );
//
//                totalScore -= 0.1;
//            }
//        }
