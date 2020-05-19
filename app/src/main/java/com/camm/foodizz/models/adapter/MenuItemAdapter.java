package com.camm.foodizz.models.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ItemViewHolder> {

    private ArrayList<Food> listFoodsOnMenu;
    private Context context;

    public MenuItemAdapter(ArrayList<Food> listFoodsOnMenu, Context context) {
        this.listFoodsOnMenu = listFoodsOnMenu;
        this.context = context;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imgRestaurantMenuItem;
        TextView txtRestaurantItemName, txtRestaurantItemRate, txtRestaurantItemPrice;
        CheckBox cbRestaurantMenuItem;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRestaurantMenuItem = itemView.findViewById(R.id.imgRestaurantMenuItem);
            txtRestaurantItemName = itemView.findViewById(R.id.txtRestaurantItemName);
            txtRestaurantItemRate = itemView.findViewById(R.id.txtRestaurantItemRate);
            txtRestaurantItemPrice = itemView.findViewById(R.id.txtRestaurantItemPrice);
            cbRestaurantMenuItem = itemView.findViewById(R.id.cbRestaurantMenuItem);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.txtRestaurantItemName.setText(listFoodsOnMenu.get(position).getFoodName());
        holder.txtRestaurantItemPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(listFoodsOnMenu.get(position).getFoodPrice())));
        Picasso.get().load(listFoodsOnMenu.get(position).getFoodSquareImageUri()).into(holder.imgRestaurantMenuItem);
        holder.txtRestaurantItemRate.setText(new DecimalFormat("0.0").format(listFoodsOnMenu.get(position).getTotalScore()));
        holder.cbRestaurantMenuItem.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return listFoodsOnMenu.size();
    }

}
