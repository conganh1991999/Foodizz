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

public class RestaurantReviewAdapter extends RecyclerView.Adapter<RestaurantReviewAdapter.ReviewViewHolder> {

    private ArrayList<UserReview> listReviews;
    private Context context;

    public RestaurantReviewAdapter(ArrayList<UserReview> listReviews, Context context) {
        this.listReviews = listReviews;
        this.context = context;
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{
        CircleImageView personImageOnRestaurant;
        TextView personNameOnRestaurant, txtTimeOfRestaurantReview, personReviewOnRestaurant;
        RatingBar personRbOnRestaurant;
        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            personImageOnRestaurant = itemView.findViewById(R.id.personImageOnRestaurant);
            personNameOnRestaurant = itemView.findViewById(R.id.personNameOnRestaurant);
            txtTimeOfRestaurantReview = itemView.findViewById(R.id.txtTimeOfRestaurantReview);
            personReviewOnRestaurant = itemView.findViewById(R.id.personReviewOnRestaurant);
            personRbOnRestaurant = itemView.findViewById(R.id.personRbOnRestaurant);
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.personReviewOnRestaurant.setText(listReviews.get(position).getReview());
        holder.personRbOnRestaurant.setRating((float) listReviews.get(position).getRating());
        SimpleDateFormat sfd = new SimpleDateFormat("dd MMMM yyyy");
        holder.txtTimeOfRestaurantReview.setText(sfd.format(new Date(listReviews.get(position).getTimestamp()*1000)));
        Picasso.get().load(listReviews.get(position).getUserImage()).into(holder.personImageOnRestaurant);
        holder.personNameOnRestaurant.setText(listReviews.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return listReviews.size();
    }

}
