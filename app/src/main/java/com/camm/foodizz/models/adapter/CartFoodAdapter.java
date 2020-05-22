package com.camm.foodizz.models.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.room.entity.CartFood;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class CartFoodAdapter extends ListAdapter<CartFood, CartFoodAdapter.CartFoodViewHolder> {
    private OnItemClickListener listener;

    public CartFoodAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CartFood> DIFF_CALLBACK = new DiffUtil.ItemCallback<CartFood>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartFood oldItem, @NonNull CartFood newItem) {
            return oldItem.getFoodId().equals(newItem.getFoodId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartFood oldItem, @NonNull CartFood newItem) {
            return oldItem.getQuantity() == newItem.getQuantity() &&
                    oldItem.getTotalScore() == newItem.getTotalScore();
        }
    };


    class CartFoodViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCartFood;
        TextView txtCartFoodName, txtCartFoodPrice;
        RatingBar rbCartFood;
        NotificationBadge cartFoodBadge;
        CartFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCartFood = itemView.findViewById(R.id.imgCartFood);
            txtCartFoodName = itemView.findViewById(R.id.txtCartFoodName);
            txtCartFoodPrice = itemView.findViewById(R.id.txtCartFoodPrice);
            rbCartFood = itemView.findViewById(R.id.rbCartFood);
            cartFoodBadge = itemView.findViewById(R.id.cartFoodBadge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position).getFoodId(), getItem(position).getQuantity());
                }
            });
        }
    }

    public CartFood getCartFoodAt(int position){
        return getItem(position);
    }

    @NonNull
    @Override
    public CartFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartFoodViewHolder holder, int position) {
        CartFood food = getItem(position);
        Picasso.get().load(food.getFoodImage()).into(holder.imgCartFood);
        holder.txtCartFoodName.setText(food.getFoodName());
        holder.txtCartFoodPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(food.getFoodPrice())));
        holder.cartFoodBadge.setNumber(food.getQuantity());
        holder.rbCartFood.setRating((float) food.getTotalScore());
    }

    public interface OnItemClickListener{
        void onItemClick(String foodId, int quantity);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
