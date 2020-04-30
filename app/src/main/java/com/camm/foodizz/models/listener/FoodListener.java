package com.camm.foodizz.models.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.FoodAdapter;
import com.camm.foodizz.ui.home_menu.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class FoodListener implements ChildEventListener {

    private ArrayList<Food> listFood;
    private FoodAdapter foodAdapter;

    public FoodListener(ArrayList<Food> listFood, FoodAdapter foodAdapter) {
        this.listFood = listFood;
        this.foodAdapter = foodAdapter;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Food data = new Food.FoodBuilder(
                dataSnapshot.getKey(),
                dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("price").getValue(Double.class),
                dataSnapshot.child("totalScore").getValue(Double.class),
                dataSnapshot.child("numOfRate").getValue(Integer.class))
                .setFoodSquareImageUri(dataSnapshot.child("squareImage").getValue(String.class))
                .build();
        listFood.add(data);
        foodAdapter.notifyDataSetChanged();
        HomeFragment.isScrollingFood = false;
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
