package com.camm.foodizz.data.listener;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.adapter.FoodReviewAdapter;
import com.camm.foodizz.models.Review;
import com.camm.foodizz.ui.food_detail.FoodReviewFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ListReviewListener implements ChildEventListener {

    private static final String TAG = "ListReviewListener";

    private ArrayList<Review> listReview;
    private FoodReviewAdapter foodReviewAdapter;
    private int oldListSize;

    public ListReviewListener(ArrayList<Review> listReview, FoodReviewAdapter foodReviewAdapter) {
        this.listReview = listReview;
        this.foodReviewAdapter = foodReviewAdapter;
        oldListSize = listReview.size();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Review review = new Review(
                dataSnapshot.child("userId").getValue(String.class),
                dataSnapshot.child("review").getValue(String.class),
                dataSnapshot.child("rating").getValue(Double.class),
                dataSnapshot.child("timestamp").getValue(Long.class)
        );
        if(FoodReviewFragment.isReviewInserted){
            oldListSize += 1;
            listReview.add(review);
            FoodReviewFragment.isReviewInserted = false;
        }
        else
        {
            if(listReview.size() < (oldListSize + 4)){
                listReview.add(review);
            }
            else {
                FoodReviewFragment.nextReviewItemKey = dataSnapshot.getKey();
                FoodReviewFragment.isScrollingReview = true;
            }
        }
        foodReviewAdapter.notifyDataSetChanged();
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
