package com.camm.foodizz.ui.food_detail;

import android.os.Bundle;
import android.util.Log;
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

    private TextView txtDetailOfFood;
    private RatingBar rbFoodDetail;
    private Button btnAddToBag, btnIncreaseFood, btnDecreaseFood;
    private NotificationBadge mBadge;

    private static int QUANTITY = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_details, container, false);
        mapping();

        mBadge.setNumber(QUANTITY);

        btnIncreaseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QUANTITY += 1;
                mBadge.setNumber(QUANTITY);
            }
        });

        btnDecreaseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QUANTITY -= 1;
                if(QUANTITY < 0)
                    QUANTITY = 0;
                mBadge.setNumber(QUANTITY);
            }
        });

        btnAddToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QUANTITY = 0;
                mBadge.setNumber(QUANTITY);
                Toast.makeText(getContext(),"Added", Toast.LENGTH_SHORT).show();
                // TODO: Send orders to Cart
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null) {
            FoodDetailViewModel foodModel = new ViewModelProvider(getActivity()).get(FoodDetailViewModel.class);
            foodModel.getFood().observe(getViewLifecycleOwner(), new Observer<Food>() {
                @Override
                public void onChanged(Food food) {
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
