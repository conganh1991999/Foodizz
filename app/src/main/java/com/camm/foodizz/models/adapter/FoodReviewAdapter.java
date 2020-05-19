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
import com.camm.foodizz.models.UserReview;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodReviewAdapter extends RecyclerView.Adapter<FoodReviewAdapter.ReviewViewHolder> {

    private ArrayList<UserReview> listReviews;
    private Context context;

    public FoodReviewAdapter(ArrayList<UserReview> listReviews, Context context) {
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
        holder.personReviewOnFoodDetail.setText(listReviews.get(position).getReview());
        holder.personRbOnFoodDetail.setRating((float) listReviews.get(position).getRating());
        SimpleDateFormat sfd = new SimpleDateFormat("dd MMMM yyyy");
        holder.txtTimeOfFoodReview.setText(sfd.format(new Date(listReviews.get(position).getTimestamp()*1000)));
        Picasso.get().load(listReviews.get(position).getUserImage()).into(holder.personImageOnFoodDetail);
        holder.personNameOnFoodDetail.setText(listReviews.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return listReviews.size();
    }

}
