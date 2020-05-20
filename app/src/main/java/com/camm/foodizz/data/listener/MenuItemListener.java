package com.camm.foodizz.data.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.MenuItemAdapter;
import com.camm.foodizz.ui.home_menu.profile.FavouriteFragment;
import com.camm.foodizz.ui.restaurant.RestaurantMenuFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuItemListener implements ChildEventListener {

    private ArrayList<Food> listFoodOnMenu;
    private MenuItemAdapter menuAdapter;
    private String menu;
    private int counter = 5;

    public MenuItemListener(ArrayList<Food> listFoodOnMenu, MenuItemAdapter menuAdapter, String menu) {
        this.listFoodOnMenu = listFoodOnMenu;
        this.menuAdapter = menuAdapter;
        this.menu = menu;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if(counter > 1){
            final String foodId = dataSnapshot.getValue(String.class);
            if(foodId != null){
                DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("foods")
                        .child(foodId);
                foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Food food = new Food(
                                dataSnapshot.getKey(),
                                dataSnapshot.child("name").getValue(String.class),
                                dataSnapshot.child("price").getValue(Double.class),
                                dataSnapshot.child("totalScore").getValue(Double.class),
                                dataSnapshot.child("numOfRate").getValue(Integer.class));
                        food.setFoodSquareImageUri(dataSnapshot.child("squareImage").getValue(String.class));
                        listFoodOnMenu.add(food);
                        menuAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            counter -= 1;
        }
        else {
            if(menu.equals("RestaurantMenu"))
            {
                RestaurantMenuFragment.nextMenuFoodItemKey = dataSnapshot.getKey();
                RestaurantMenuFragment.isScrollingMenu = true;
            }
            else if(menu.equals("FavouriteMenu")){
                FavouriteFragment.nextMenuFoodItemKey = dataSnapshot.getKey();
                FavouriteFragment.isScrollingMenu = true;
            }
        }
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
