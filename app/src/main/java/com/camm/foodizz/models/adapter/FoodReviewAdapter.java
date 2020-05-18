package com.camm.foodizz.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodReviewAdapter extends RecyclerView.Adapter<FoodReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> listReviews;
    private Context context;

    private String userId = "";
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");
    private ValueEventListener listener;

    public FoodReviewAdapter(ArrayList<Review> listReviews, Context context) {
        this.listReviews = listReviews;
        this.context = context;
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{
        CircleImageView personImageOnFoodDetail;
        TextView personNameOnFoodDetail, txtTimeOfFoodReview, personReviewOnFoodDetail;
        RatingBar personRbOnFoodDetail;
        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            personImageOnFoodDetail = itemView.findViewById(R.id.personImageOnFoodDetail);
            personNameOnFoodDetail = itemView.findViewById(R.id.personNameOnFoodDetail);
            txtTimeOfFoodReview = itemView.findViewById(R.id.txtTimeOfFoodReview);
            personReviewOnFoodDetail = itemView.findViewById(R.id.personReviewOnFoodDetail);
            personRbOnFoodDetail = itemView.findViewById(R.id.personRbOnFoodDetail);
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if(!userId.isEmpty()){
            mRef.child(userId).removeEventListener(listener);
        }

        holder.personReviewOnFoodDetail.setText(listReviews.get(position).getReview());
        holder.personRbOnFoodDetail.setRating((float) listReviews.get(position).getRating());
        SimpleDateFormat sfd = new SimpleDateFormat("dd MMMM yyyy");
        holder.txtTimeOfFoodReview.setText(sfd.format(new Date(listReviews.get(position).getTimestamp()*1000)));

        userId = listReviews.get(position).getUserId();
        listener = new UserListener(holder);
        mRef.child(userId).addListenerForSingleValueEvent(listener);
    }

    @Override
    public int getItemCount() {
        return listReviews.size();
    }

    static class UserListener implements ValueEventListener{

        ReviewViewHolder holder;

        UserListener(ReviewViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Picasso.get().load(dataSnapshot.child("userImage").getValue(String.class)).into(holder.personImageOnFoodDetail);
            holder.personNameOnFoodDetail.setText(dataSnapshot.child("userName").getValue(String.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
