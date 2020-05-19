package com.camm.foodizz.ui.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Restaurant;
import com.camm.foodizz.models.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private int intendSender = 0; // 0: from FoodDetailActivity, 1: from HomeActivity

    private RestaurantViewModel restaurantModel;
    private Restaurant restaurant;

    private ImageView imgRestaurant, imgRestaurantLogo;
    private TextView txtRestaurantName, txtRestaurantRate, txtRestaurantRateNum,
            txtRestaurantCategory1, txtRestaurantCategory2, txtRestaurantCategory3,
            txtRestaurantAddress;
    private RatingBar rbRestaurant;

    private ViewPager restaurantFragmentPager;
    private TabLayout restaurantTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mapping();

        restaurantModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        getIntentData();
        if(intendSender == 0){
            restaurantModel.getRestaurant().observe(this, new Observer<Restaurant>() {
                @Override
                public void onChanged(Restaurant restaurant) {
                    restaurantModel.listenForRatingPoint();
                    updateUI(restaurant);
                }
            });
        }
        else{
            updateUI(this.restaurant);
        }
        restaurantModel.getTotalScore().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                txtRestaurantRate.setText(new DecimalFormat("0.0").format(aDouble));
                rbRestaurant.setRating(aDouble.floatValue());
            }
        });
        restaurantModel.getNumOfRate().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txtRestaurantRateNum.setText(String.format("(%s)", String.valueOf(integer)));
            }
        });

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RestaurantMenuFragment());
        fragmentList.add(new RestaurantReviewFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("Foods");
        titleList.add("Reviews");

        PagerAdapter restaurantPagerAdapter = new PagerAdapter(getSupportFragmentManager(), 1);
        restaurantPagerAdapter.addFragment(fragmentList, titleList);

        restaurantFragmentPager.setAdapter(restaurantPagerAdapter);
        restaurantTabLayout.setupWithViewPager(restaurantFragmentPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(restaurantModel != null)
            restaurantModel.removeListener();
    }

    private void mapping() {
        imgRestaurant = findViewById(R.id.imgRestaurant);
        imgRestaurantLogo = findViewById(R.id.imgRestaurantLogo);
        txtRestaurantName = findViewById(R.id.txtRestaurantName);
        txtRestaurantRate = findViewById(R.id.txtRestaurantRate);
        txtRestaurantRateNum = findViewById(R.id.txtRestaurantRateNum);
        txtRestaurantCategory1 = findViewById(R.id.txtRestaurantCategory1);
        txtRestaurantCategory2 = findViewById(R.id.txtRestaurantCategory2);
        txtRestaurantCategory3 = findViewById(R.id.txtRestaurantCategory3);
        txtRestaurantAddress = findViewById(R.id.txtRestaurantAddress);
        rbRestaurant = findViewById(R.id.rbRestaurant);
        restaurantTabLayout = findViewById(R.id.restaurantTabLayout);
        restaurantFragmentPager = findViewById(R.id.restaurantFragmentPager);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        String restaurantId = intent.getStringExtra("restaurantId");
        if(restaurantId == null){
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            if(restaurant != null) {
                intendSender = 1;
                restaurantModel.setRestaurant(restaurant.getRestaurantId(), intendSender);
            }
        }
        else{
            intendSender = 0;
            restaurantModel.setRestaurant(restaurantId, intendSender);
        }
    }

    private void updateUI(Restaurant restaurant){
        Picasso.get().load(restaurant.getRestaurantImageUri()).into(imgRestaurant);
        Picasso.get().load(restaurant.getRestaurantLogoUri()).into(imgRestaurantLogo);
        txtRestaurantName.setText(restaurant.getRestaurantName());
        txtRestaurantRate.setText(new DecimalFormat("0.0").format(restaurant.getTotalScore()));
        txtRestaurantRateNum.setText(String.format("(%s)", String.valueOf(restaurant.getNumOfRate())));
        txtRestaurantCategory1.setText(restaurant.getCategoryName().get(0));
        txtRestaurantCategory2.setText(restaurant.getCategoryName().get(1));
        txtRestaurantCategory3.setText(restaurant.getCategoryName().get(2));
        txtRestaurantAddress.setText(restaurant.getRestaurantAddress());
        rbRestaurant.setRating((float) restaurant.getTotalScore());
    }

}
