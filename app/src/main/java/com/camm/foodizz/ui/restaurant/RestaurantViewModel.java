package com.camm.foodizz.ui.restaurant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.camm.foodizz.models.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantViewModel extends AndroidViewModel {

    private MutableLiveData<Restaurant> restaurantLiveData;
    private MutableLiveData<Double> restaurantTotalScore;
    private MutableLiveData<Integer> restaurantNumOfRate;

    private DatabaseReference restaurantRef;
    private ValueEventListener restaurantListener;
    private String restaurantId;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        restaurantLiveData = new MutableLiveData<>();
        restaurantTotalScore = new MutableLiveData<>();
        restaurantNumOfRate = new MutableLiveData<>();
    }

    void setRestaurant(String restaurantId, int task) {
        this.restaurantId = restaurantId;
        restaurantRef = FirebaseDatabase.getInstance()
                .getReference("restaurants")
                .child(restaurantId);
        if(task == 1)
            listenForRatingPoint();
        else
            listenForRestaurantItem();
    }

    private void listenForRestaurantItem(){
        removeListener();
        restaurantListener = new RestaurantItemListener();
        restaurantRef.addListenerForSingleValueEvent(restaurantListener);
    }

    void listenForRatingPoint(){
        removeListener();
        restaurantListener = new RestaurantRatingPointListener();
        restaurantRef.addValueEventListener(restaurantListener);
    }

    void removeListener(){
        if(restaurantRef != null && restaurantListener != null)
            restaurantRef.removeEventListener(restaurantListener);
    }

    String getRestaurantId(){
        return restaurantId;
    }

    MutableLiveData<Restaurant> getRestaurant(){
        return restaurantLiveData;
    }

    MutableLiveData<Double> getTotalScore(){
        return restaurantTotalScore;
    }

    MutableLiveData<Integer> getNumOfRate(){
        return restaurantNumOfRate;
    }


    private class RestaurantItemListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            ArrayList<String> categoryName = new ArrayList<>();
            for(int i = 0; i < 3; i++){
                categoryName.add(dataSnapshot.child("categoryName").child(String.valueOf(i)).getValue(String.class));
            }
            Double totalScore = dataSnapshot.child("totalScore").getValue(Double.class);
            Integer numOfRate = dataSnapshot.child("numOfRate").getValue(Integer.class);
            Restaurant restaurantItem = new Restaurant(
                    dataSnapshot.getKey(),
                    dataSnapshot.child("name").getValue(String.class),
                    dataSnapshot.child("logo").getValue(String.class),
                    dataSnapshot.child("image").getValue(String.class),
                    dataSnapshot.child("address").getValue(String.class),
                    categoryName, totalScore, numOfRate);

            restaurantLiveData.setValue(restaurantItem);
            restaurantTotalScore.setValue(totalScore);
            restaurantNumOfRate.setValue(numOfRate);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

    private class RestaurantRatingPointListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            restaurantTotalScore.setValue(dataSnapshot.child("totalScore").getValue(Double.class));
            restaurantNumOfRate.setValue(dataSnapshot.child("numOfRate").getValue(Integer.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
