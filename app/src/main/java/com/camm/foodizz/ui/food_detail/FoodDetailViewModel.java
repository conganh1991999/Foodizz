package com.camm.foodizz.ui.food_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.camm.foodizz.models.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Food> foodLiveData;
    private MutableLiveData<Double> foodTotalScore;
    private MutableLiveData<Integer> foodNumOfRate;

    private DatabaseReference foodRef;
    private ValueEventListener foodListener;
    private String foodId;

    public FoodDetailViewModel(@NonNull Application application) {
        super(application);
        foodLiveData = new MutableLiveData<>();
        foodTotalScore = new MutableLiveData<>();
        foodNumOfRate = new MutableLiveData<>();
    }

    void setFood(String foodId){
        this.foodId = foodId;
        foodRef = FirebaseDatabase.getInstance()
                .getReference("foods")
                .child(foodId);
        foodListener = new FoodItemListener();
        foodRef.addListenerForSingleValueEvent(foodListener);
    }

    void listenForRatingPoint(){
        removeListener();
        foodListener = new FoodRatingPointListener();
        foodRef.addValueEventListener(foodListener);
    }

    void removeListener(){
        foodRef.removeEventListener(foodListener);
    }

    String getFoodId(){
        return foodId;
    }

    MutableLiveData<Food> getFood(){
        return foodLiveData;
    }

    MutableLiveData<Double> getTotalScore(){
        return foodTotalScore;
    }

    MutableLiveData<Integer> getNumOfRate(){
        return foodNumOfRate;
    }


    private class FoodItemListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Food foodItem = new Food(dataSnapshot.getKey(),
                    dataSnapshot.child("name").getValue(String.class),
                    dataSnapshot.child("price").getValue(Double.class),
                    dataSnapshot.child("totalScore").getValue(Double.class),
                    dataSnapshot.child("numOfRate").getValue(Integer.class));

            foodItem.setFoodDetail(dataSnapshot.child("detail").getValue(String.class));
            foodItem.setRestaurantName(dataSnapshot.child("restaurantName").getValue(String.class));
            foodItem.setRestaurantId(dataSnapshot.child("restaurantId").getValue(String.class));

            ArrayList<String> foodLandscapeImageUri = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                foodLandscapeImageUri.add(dataSnapshot.child("landscapeImage").child(String.valueOf(i))
                        .getValue(String.class));
            }
            foodItem.setFoodLandscapeImageUri(foodLandscapeImageUri);

            foodLiveData.setValue(foodItem);
            foodTotalScore.setValue(dataSnapshot.child("totalScore").getValue(Double.class));
            foodNumOfRate.setValue(dataSnapshot.child("numOfRate").getValue(Integer.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

    private class FoodRatingPointListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            foodTotalScore.setValue(dataSnapshot.child("totalScore").getValue(Double.class));
            foodNumOfRate.setValue(dataSnapshot.child("numOfRate").getValue(Integer.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
