package com.camm.foodizz.models.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.Category;
import com.camm.foodizz.models.adapter.CategoryAdapter;
import com.camm.foodizz.ui.home_menu.home.HomeFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class CategoryListener implements ChildEventListener {

    private ArrayList<Category> listCategory;
    private CategoryAdapter categoryAdapter;

    public CategoryListener(ArrayList<Category> listCategory, CategoryAdapter categoryAdapter) {
        this.listCategory = listCategory;
        this.categoryAdapter = categoryAdapter;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Category data = new Category(
                dataSnapshot.child("name").getValue(String.class),
                dataSnapshot.child("image").getValue(String.class));
        listCategory.add(data);
        categoryAdapter.notifyDataSetChanged();
        HomeFragment.isScrollingCategory = false;
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