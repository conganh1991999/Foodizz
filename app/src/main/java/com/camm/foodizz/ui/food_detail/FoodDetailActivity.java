package com.camm.foodizz.ui.food_detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.PagerAdapter;
import com.camm.foodizz.models.adapter.SliderAdapter;
import com.camm.foodizz.ui.restaurant.RestaurantActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FoodDetailActivity extends AppCompatActivity {

    private static final String TAG = "FoodDetailActivity";

    private FoodDetailViewModel foodModel;

    private TextView txtRestaurantHasFood, txtDetailFoodName, txtDetailFoodPrice;
    private ViewPager foodDetailImagesPager;
    private CircleIndicator indicator;

    private ViewPager foodDetailFragmentPager;
    private TabLayout foodDetailTabLayout;

    private int currentPosition = 0;
    private boolean sliderCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapping();

        foodModel = new ViewModelProvider(this).get(FoodDetailViewModel.class);
        getIntentData();
        foodModel.getFood().observe(this, new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                foodModel.listenForRatingPoint();
                updateUI(food);
            }
        });

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FoodDetailFragment());
        fragmentList.add(new FoodReviewFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("Details");
        titleList.add("Reviews");

        PagerAdapter foodDetailPagerAdapter = new PagerAdapter(getSupportFragmentManager(), 1);
        foodDetailPagerAdapter.addFragment(fragmentList, titleList);

        foodDetailFragmentPager.setAdapter(foodDetailPagerAdapter);
        foodDetailTabLayout.setupWithViewPager(foodDetailFragmentPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(foodModel != null){
            foodModel.removeListener();
        }
    }

    private void mapping(){
        txtDetailFoodName = findViewById(R.id.txtDetailFoodName);
        txtDetailFoodPrice = findViewById(R.id.txtDetailFoodPrice);
        txtRestaurantHasFood = findViewById(R.id.txtRestaurantHasFood);
        foodDetailImagesPager = findViewById(R.id.foodDetailImagesPager);
        indicator = findViewById(R.id.indicatorFoodDetail);
        foodDetailFragmentPager = findViewById(R.id.foodDetailFragmentPager);
        foodDetailTabLayout = findViewById(R.id.foodDetailTabLayout);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        String foodId = intent.getStringExtra("foodId");
        if (foodId != null) {
            if(foodModel != null){
                foodModel.setFood(foodId);
            }
        } else {
            Log.d(TAG, "FoodDetailActivity: food id is null");
        }
    }

    private void updateUI(Food food){
        txtRestaurantHasFood.setText(food.getRestaurantName());
        setOnRestaurantNameClick(food.getRestaurantId());

        txtDetailFoodName.setText(food.getFoodName());
        txtDetailFoodPrice.setText(String.format("$%s",
                new DecimalFormat("######.00").format(food.getFoodPrice())));

        createSliderShow(food.getFoodLandscapeImageUri());
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

    private void createSliderShow(ArrayList<String> foodLandscapeImageUri) {
        if(!sliderCreated){
            foodDetailImagesPager.setAdapter(new SliderAdapter(FoodDetailActivity.this, foodLandscapeImageUri));
            indicator.setViewPager(foodDetailImagesPager);
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPosition == 2) {
                        currentPosition = 0;
                    } else {
                        currentPosition += 1;
                    }
                    foodDetailImagesPager.setCurrentItem(currentPosition, true);
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
        sliderCreated = true;
    }

}
