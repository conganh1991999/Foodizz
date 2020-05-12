package com.camm.foodizz.data.listener;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Category;
import com.camm.foodizz.models.adapter.CategoryAdapter;
import com.camm.foodizz.ui.home_menu.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ListCategoryListener implements ChildEventListener {

    private ArrayList<Category> listCategory;
    private CategoryAdapter categoryAdapter;
    private int oldListSize;

    public ListCategoryListener(ArrayList<Category> listCategory, CategoryAdapter categoryAdapter) {
        this.listCategory = listCategory;
        this.categoryAdapter = categoryAdapter;
        this.oldListSize = listCategory.size();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d("ListenerCategory", "Changed!");
        Category data = new Category(
                dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("image").getValue(String.class));
        if(listCategory.size() < (oldListSize + 4)){
            listCategory.add(data);
        }
        else {
            HomeFragment.nextCategoryItemKey = dataSnapshot.getKey();
            HomeFragment.isScrollingCategory = false;
        }
        categoryAdapter.notifyDataSetChanged();
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