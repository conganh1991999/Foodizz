package com.camm.foodizz.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

// Adapter for RecyclerView
public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    private ArrayList<Food> listFoods;
    private Context context;

    private OnItemClickListener listener;

    public FoodAdapter(ArrayList<Food> listFoods, Context context) {
        this.listFoods = listFoods;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomeFood;
        CheckBox cbHomeFood;
        TextView txtHomeFoodName, txtHomeFoodPrice;
        TextView txtHomeFoodRate;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHomeFood = itemView.findViewById(R.id.imgHomeFood);
            cbHomeFood = itemView.findViewById(R.id.cbHomeFood);
            txtHomeFoodName = itemView.findViewById(R.id.txtHomeFoodName);
            txtHomeFoodPrice = itemView.findViewById(R.id.txtHomeFoodPrice);
            txtHomeFoodRate = itemView.findViewById(R.id.txtHomeFoodRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(listFoods.get(position));
                }
            });
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressFood);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.food_item_home, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.food_scroll, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listFoods.size();
    }


    // The following method decides the type of ViewHolder to display in the RecyclerView
    @Override
    public int getItemViewType(int position) {
        final int VIEW_TYPE_LOADING = 1;
        return listFoods.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder holder, int position) {
        holder.txtHomeFoodName.setText(listFoods.get(position).getFoodName());
        holder.txtHomeFoodPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(listFoods.get(position).getFoodPrice())));
        Picasso.get().load(listFoods.get(position).getFoodSquareImageUri()).into(holder.imgHomeFood);
        holder.txtHomeFoodRate.setText(new DecimalFormat("0.0").format(listFoods.get(position).getTotalScore()));
        holder.cbHomeFood.setChecked(false);
    }

    public interface OnItemClickListener{
        void onItemClick(Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}

