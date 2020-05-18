package com.camm.foodizz.data.listener;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.FoodAdapter;
import com.camm.foodizz.ui.home_menu.HomeMenuActivity;
import com.camm.foodizz.ui.home_menu.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ListFoodListener implements ChildEventListener {

    private ArrayList<Food> listFood;
    private FoodAdapter foodAdapter;
    private int oldListSize;

    public ListFoodListener(ArrayList<Food> listFood, FoodAdapter foodAdapter) {
        this.listFood = listFood;
        this.foodAdapter = foodAdapter;
        this.oldListSize = listFood.size();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Food data = new Food(
                dataSnapshot.getKey(),
                dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("price").getValue(Double.class),
                dataSnapshot.child("totalScore").getValue(Double.class),
                dataSnapshot.child("numOfRate").getValue(Integer.class));
                data.setFoodSquareImageUri(dataSnapshot.child("squareImage").getValue(String.class));
        if(listFood.size() < (oldListSize + 4)){
            listFood.add(data);
        }
        else {
            HomeFragment.nextFoodItemKey = dataSnapshot.getKey();
            HomeFragment.isScrollingFood = true;
        }
        foodAdapter.notifyDataSetChanged();
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
