package com.camm.foodizz.models.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Restaurant;
import com.camm.foodizz.models.adapter.RestaurantAdapter;
import com.camm.foodizz.ui.home_menu.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class RestaurantListener implements ChildEventListener {

    private ArrayList<Restaurant> listRestaurant;
    private RestaurantAdapter restaurantAdapter;

    public RestaurantListener(ArrayList<Restaurant> listRestaurant, RestaurantAdapter restaurantAdapter) {
        this.listRestaurant = listRestaurant;
        this.restaurantAdapter = restaurantAdapter;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        ArrayList<String> categoryName = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            categoryName.add(dataSnapshot.child("categoryName").child(String.valueOf(i)).getValue(String.class));
        }
        Restaurant data = new Restaurant(
                dataSnapshot.getKey(),
                dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("logo").getValue(String.class),
                dataSnapshot.child("image").getValue(String.class),
                categoryName,
                dataSnapshot.child("totalScore").getValue(Double.class),
                dataSnapshot.child("numOfRate").getValue(Integer.class));
        listRestaurant.add(data);
        restaurantAdapter.notifyDataSetChanged();
        HomeFragment.isScrollingRestaurant = false;
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

}
