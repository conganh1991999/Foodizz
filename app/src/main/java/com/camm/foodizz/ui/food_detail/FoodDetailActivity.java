package com.camm.foodizz.ui.food_detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.SliderAdapter;
import com.camm.foodizz.ui.restaurant.RestaurantActivity;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FoodDetailActivity extends AppCompatActivity {

    private static final String TAG = "FoodDetailActivity";

    private FoodDetailViewModel foodModel;

    private TextView txtRestaurantHasFood, txtDetailFoodName, txtDetailFoodPrice;
    private ViewPager viewPager;
    private CircleIndicator indicator;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodModel = new ViewModelProvider(this).get(FoodDetailViewModel.class);

        mapping();

        getIntentData();

        foodModel.getFood().observe(this, new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                updateUI(food);
            }
        });
    }

    private void mapping(){
        txtDetailFoodName = findViewById(R.id.txtDetailFoodName);
        txtDetailFoodPrice = findViewById(R.id.txtDetailFoodPrice);
        txtRestaurantHasFood = findViewById(R.id.txtRestaurantHasFood);
        viewPager = findViewById(R.id.viewPagerFoodDetail);
        indicator = findViewById(R.id.indicatorFoodDetail);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        String foodId = intent.getStringExtra("foodId");
        if (foodId != null) {
            if(foodModel != null){
                foodModel.setFood(foodId);
            }
        } else {
            Log.d(TAG, "food object is null");
        }
    }

    private void updateUI(Food food){
        txtRestaurantHasFood.setText(food.getRestaurantName());
        setOnRestaurantNameClick(food.getRestaurantId());

        txtDetailFoodName.setText(food.getFoodName());
        txtDetailFoodPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(food.getFoodPrice())));

        viewPager.setAdapter(new SliderAdapter(FoodDetailActivity.this, food.getFoodLandscapeImageUri()));
        indicator.setViewPager(viewPager);
        createSliderShow();

    }

    private void setOnRestaurantNameClick(final String restaurantId){
        txtRestaurantHasFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetailActivity.this, RestaurantActivity.class);
                intent.putExtra("restaurantId", restaurantId);
                startActivity(intent);
            }
        });
    }

    private void createSliderShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == 2) {
                    currentPosition = 0;
                } else {
                    currentPosition += 1;
                }
                viewPager.setCurrentItem(currentPosition, true);
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }

}
