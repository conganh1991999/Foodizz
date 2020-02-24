package com.camm.foodizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import com.camm.foodizz.models.SliderAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class FoodDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator indicator;

    private ArrayList<String> colorName;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);

        colorName = new ArrayList<>();
        colorName.add("RED");
        colorName.add("GREEN");
        colorName.add("BLUE");

        viewPager.setAdapter(new SliderAdapter(this, colorName));
        indicator.setViewPager(viewPager);

        createSliderShow();
    }

    private void createSliderShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(currentPosition == colorName.size() - 1){
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
