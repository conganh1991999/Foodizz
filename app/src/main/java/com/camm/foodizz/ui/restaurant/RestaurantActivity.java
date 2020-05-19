package com.camm.foodizz.ui.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantActivity";

    private RestaurantViewModel restaurantModel;

    private ImageView imgRestaurant, imgRestaurantLogo;
    private TextView txtRestaurantName, txtRestaurantRate, txtRestaurantRateNum,
            txtRestaurantCategory1, txtRestaurantCategory2, txtRestaurantCategory3,
            txtRestaurantAddress;
    private RatingBar rbRestaurant;

    private TabLayout restaurantTabLayout;
    private ViewPager restaurantFragmentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mapping();





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

}
