package com.camm.foodizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import com.camm.foodizz.models.SliderAdapter;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FoodDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator indicator;

    private ArrayList<Integer> foodImage;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);

        foodImage = new ArrayList<>();
        foodImage.add(R.drawable.ooo1);
        foodImage.add(R.drawable.ooo2);
        foodImage.add(R.drawable.ooo3);

        viewPager.setAdapter(new SliderAdapter(this, foodImage));
        indicator.setViewPager(viewPager);

        NotificationBadge mBadge = findViewById(R.id.mBadge);
        mBadge.setNumber(1);

        createSliderShow();
    }

    private void createSliderShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(currentPosition == foodImage.size() - 1){
                    currentPosition = 0;
                }
                else{
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
