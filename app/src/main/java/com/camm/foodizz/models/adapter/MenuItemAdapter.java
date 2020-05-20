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
        ImageView imgMenuItem;
        TextView txtItemName, txtItemRate, txtItemPrice;
        CheckBox cbMenuItem;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMenuItem = itemView.findViewById(R.id.imgMenuItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemRate = itemView.findViewById(R.id.txtItemRate);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            cbMenuItem = itemView.findViewById(R.id.cbMenuItem);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.txtItemName.setText(listFoodsOnMenu.get(position).getFoodName());
        holder.txtItemPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(listFoodsOnMenu.get(position).getFoodPrice())));
        Picasso.get().load(listFoodsOnMenu.get(position).getFoodSquareImageUri()).into(holder.imgMenuItem);
        holder.txtItemRate.setText(new DecimalFormat("0.0").format(listFoodsOnMenu.get(position).getTotalScore()));
        holder.cbMenuItem.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return listFoodsOnMenu.size();
    }

}
