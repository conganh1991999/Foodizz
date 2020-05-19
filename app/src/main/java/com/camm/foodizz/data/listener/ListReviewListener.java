package com.camm.foodizz.data.listener;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.camm.foodizz.models.UserReview;
import com.camm.foodizz.models.adapter.FoodReviewAdapter;
import com.camm.foodizz.models.Review;
import com.camm.foodizz.ui.food_detail.FoodReviewFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListReviewListener implements ChildEventListener {

    private ArrayList<UserReview> listReview;
    private FoodReviewAdapter foodReviewAdapter;
    private int counter = 5;

    public ListReviewListener(ArrayList<UserReview> listReview, FoodReviewAdapter foodReviewAdapter) {
        this.listReview = listReview;
        this.foodReviewAdapter = foodReviewAdapter;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if (counter > 1) {
            String userId = dataSnapshot.child("userId").getValue(String.class);
            if(userId != null){
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                        .child(userId);
                userRef.addListenerForSingleValueEvent(new UserListener(
                        dataSnapshot.child("review").getValue(String.class),
                        dataSnapshot.child("rating").getValue(Double.class),
                        dataSnapshot.child("timestamp").getValue(Long.class)
                ));
            }
            if(FoodReviewFragment.isReviewInserted)
                FoodReviewFragment.isReviewInserted = false;
            else
                counter -= 1;
        } else {
            FoodReviewFragment.nextReviewItemKey = dataSnapshot.getKey();
            FoodReviewFragment.isScrollingReview = true;
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

    private class UserListener implements ValueEventListener {

        private String review;
        private double rating;
        private long timestamp;

        UserListener(String review, double rating, long timestamp) {
            this.review = review;
            this.rating = rating;
            this.timestamp = timestamp;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            UserReview userReview = new UserReview(
                    dataSnapshot.child("userName").getValue(String.class),
                    dataSnapshot.child("userImage").getValue(String.class),
                    review,
                    rating,
                    timestamp);
            listReview.add(userReview);
            foodReviewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
