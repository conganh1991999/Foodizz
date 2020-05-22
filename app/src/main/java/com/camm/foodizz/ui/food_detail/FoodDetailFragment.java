package com.camm.foodizz.ui.food_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.nex3z.notificationbadge.NotificationBadge;

public class FoodDetailFragment extends Fragment {

    private View view;

    private FoodDetailViewModel foodModel;

    private TextView txtDetailOfFood;
    private RatingBar rbFoodDetail;
    private Button btnAddToBag, btnIncreaseFood, btnDecreaseFood;
    private NotificationBadge mBadge;

    private String foodId;
    private int quantity = -1;

    private boolean isFoodDataAvailable = false;
    private boolean isFoodAdded = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_details, container, false);
        mapping();

        btnIncreaseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity != -1){
                    quantity += 1;
                    mBadge.setNumber(quantity);
                }
            }
        });

        btnDecreaseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity != -1){
                    quantity -= 1;
                    if(quantity < 0)
                        quantity = 0;
                    mBadge.setNumber(quantity);
                }
            }
        });

        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFoodDataAvailable && quantity > 0){
                    if(isFoodAdded){
                        foodModel.updateQuantity(foodId, quantity);
                        Toast.makeText(getContext(),"Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        foodModel.saveFoodToCart(quantity);
                        Toast.makeText(getContext(),"Added", Toast.LENGTH_SHORT).show();
                    }
                    quantity = 0;
                    mBadge.setNumber(quantity);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null) {

            Intent intent = getActivity().getIntent();
            quantity = intent.getIntExtra("quantity", 0);
            if(quantity > 0)
                isFoodAdded = true;
            mBadge.setNumber(quantity);

            foodModel = new ViewModelProvider(getActivity()).get(FoodDetailViewModel.class);
            foodModel.getFood().observe(getViewLifecycleOwner(), new Observer<Food>() {
                @Override
                public void onChanged(Food food) {
                    isFoodDataAvailable = true;
                    foodId = food.getFoodId();
                    txtDetailOfFood.setText(food.getFoodDetail());
                }
            });
            foodModel.getTotalScore().observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    rbFoodDetail.setRating(aDouble.floatValue());
                }
            });
        }
    }

    private void mapping(){
        mBadge = view.findViewById(R.id.foodBadge);
        txtDetailOfFood = view.findViewById(R.id.txtDetailOfFood);
        btnAddToBag = view.findViewById(R.id.btnAddToBag);
        btnIncreaseFood = view.findViewById(R.id.btnIncreaseFood);
        btnDecreaseFood = view.findViewById(R.id.btnDecreaseFood);
        rbFoodDetail = view.findViewById(R.id.rbFoodDetail);
    }

}
