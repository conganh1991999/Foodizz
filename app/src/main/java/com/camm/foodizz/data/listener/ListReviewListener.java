package com.camm.foodizz.data.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.camm.foodizz.models.UserReview;
import com.camm.foodizz.models.adapter.FoodReviewAdapter;
import com.camm.foodizz.models.adapter.RestaurantReviewAdapter;
import com.camm.foodizz.ui.food_detail.FoodReviewFragment;
import com.camm.foodizz.ui.restaurant.RestaurantReviewFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListReviewListener implements ChildEventListener {

    private ArrayList<UserReview> listReview;
    private Adapter adapter;
    private int counter = 5;

    public ListReviewListener(ArrayList<UserReview> listReview, Adapter adapter) {
        this.listReview = listReview;
        this.adapter = adapter;
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
            if(adapter instanceof FoodReviewAdapter){
                if(FoodReviewFragment.isReviewInserted)
                    FoodReviewFragment.isReviewInserted = false;
                else
                    counter -= 1;
            }
            else if (adapter instanceof RestaurantReviewAdapter){
                if(RestaurantReviewFragment.isReviewInserted)
                    RestaurantReviewFragment.isReviewInserted = false;
                else
                    counter -= 1;
            }
        } else {
            if(adapter instanceof FoodReviewAdapter){
                FoodReviewFragment.nextReviewItemKey = dataSnapshot.getKey();
                FoodReviewFragment.isScrollingReview = true;
            }
            else if (adapter instanceof RestaurantReviewAdapter){
                RestaurantReviewFragment.nextReviewItemKey = dataSnapshot.getKey();
                RestaurantReviewFragment.isScrollingReview = true;
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
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
