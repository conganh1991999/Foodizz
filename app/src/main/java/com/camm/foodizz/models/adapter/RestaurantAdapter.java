package com.camm.foodizz.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private ArrayList<Restaurant> listRestaurant;
    private Context context;

    private OnItemClickListener listener;

    public RestaurantAdapter(ArrayList<Restaurant> listRestaurant, Context context) {
        this.listRestaurant = listRestaurant;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomeRestaurantLogo;
        TextView txtHomeRestaurantName;
        RatingBar rbHomeRestaurant;
        TextView txtHomeRestaurantRate, txtHomeRestaurantRateNum, txtHomeRestaurantAddress;
        TextView txtHomeRestaurantCategory1, txtHomeRestaurantCategory2, txtHomeRestaurantCategory3;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHomeRestaurantLogo = itemView.findViewById(R.id.imgHomeRestaurantLogo);
            txtHomeRestaurantName = itemView.findViewById(R.id.txtHomeRestaurantName);
            rbHomeRestaurant = itemView.findViewById(R.id.rbHomeRestaurant);
            txtHomeRestaurantRate = itemView.findViewById(R.id.txtHomeRestaurantRate);
            txtHomeRestaurantRateNum = itemView.findViewById(R.id.txtHomeRestaurantRateNum);
            txtHomeRestaurantCategory1 = itemView.findViewById(R.id.txtHomeRestaurantCategory1);
            txtHomeRestaurantCategory2 = itemView.findViewById(R.id.txtHomeRestaurantCategory2);
            txtHomeRestaurantCategory3 = itemView.findViewById(R.id.txtHomeRestaurantCategory3);
            txtHomeRestaurantAddress = itemView.findViewById(R.id.txtHomeRestaurantAddress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(listRestaurant.get(position));
                }
            });

        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressRestaurant);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item_home, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.restaurant_scroll, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return listRestaurant.size();
    }

    @Override
    public int getItemViewType(int position) {
        final int VIEW_TYPE_LOADING = 1;
        return listRestaurant.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder holder, int position) {
        Picasso.get().load(listRestaurant.get(position).getRestaurantLogoUri()).into(holder.imgHomeRestaurantLogo);
        holder.txtHomeRestaurantName.setText(listRestaurant.get(position).getRestaurantName());
        holder.rbHomeRestaurant.setRating((float) listRestaurant.get(position).getTotalScore());
        holder.txtHomeRestaurantRate.setText(new DecimalFormat("0.0").format(listRestaurant.get(position).getTotalScore()));
        holder.txtHomeRestaurantRateNum.setText(String.format("(%s)", String.valueOf(listRestaurant.get(position).getNumOfRate())));
        holder.txtHomeRestaurantCategory1.setText(listRestaurant.get(position).getCategoryName().get(0));
        holder.txtHomeRestaurantCategory2.setText(listRestaurant.get(position).getCategoryName().get(1));
        holder.txtHomeRestaurantCategory3.setText(listRestaurant.get(position).getCategoryName().get(2));
        holder.txtHomeRestaurantAddress.setText(listRestaurant.get(position).getRestaurantAddress());
    }

    public interface OnItemClickListener{
        void onItemClick(Restaurant restaurant);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
