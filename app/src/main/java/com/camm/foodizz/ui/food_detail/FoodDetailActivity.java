package com.camm.foodizz.ui.food_detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.SliderAdapter;
import com.camm.foodizz.ui.restaurant.RestaurantActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FoodDetailActivity extends AppCompatActivity {

    private static final String TAG = "FoodDetailActivity";

    private ViewPager viewPager;
    private CircleIndicator indicator;

    private TextView txtRestaurantHasFood;

    private ArrayList<String> foodLandscapeImageUri;

    private String foodDetail = "";
    private String restaurantId = "";

    private int currentPosition = 0;

    private static boolean has_slider = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        TextView txtDetailFoodName = findViewById(R.id.txtDetailFoodName);
        TextView txtDetailFoodPrice = findViewById(R.id.txtDetailFoodPrice);

        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("food");
        if (food != null) {
            DatabaseReference foodRef = FirebaseDatabase.getInstance()
                    .getReference("foods")
                    .child(food.getFoodId());
            foodRef.addValueEventListener(new FoodItemListener());
            txtDetailFoodName.setText(food.getFoodName());
            txtDetailFoodPrice.setText(String.format("$%s",
                    new DecimalFormat("######.00").format(food.getFoodPrice())));
        } else {
            Log.d(TAG, "food object is null");
        }

        viewPager = findViewById(R.id.viewPagerFoodDetail);
        indicator = findViewById(R.id.indicatorFoodDetail);

        foodLandscapeImageUri = new ArrayList<>();

        txtRestaurantHasFood = findViewById(R.id.txtRestaurantHasFood);
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
                if (currentPosition == foodLandscapeImageUri.size() - 1) {
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

    private class FoodItemListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            txtRestaurantHasFood.setText(dataSnapshot.child("restaurantName").getValue(String.class));

            foodDetail = dataSnapshot.child("detail").getValue(String.class);
            restaurantId = dataSnapshot.child("restaurantId").getValue(String.class);

            for (int i = 0; i < 3; i++) {
                foodLandscapeImageUri.add(dataSnapshot.child("landscapeImage").child(String.valueOf(i))
                        .getValue(String.class));
            }
            viewPager.setAdapter(new SliderAdapter(FoodDetailActivity.this, foodLandscapeImageUri));
            indicator.setViewPager(viewPager);
            createSliderShow();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

}
