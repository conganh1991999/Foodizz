package com.camm.foodizz.ui.food_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.camm.foodizz.R;

public class FoodDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        NotificationBadge mBadge = findViewById(R.id.foodBadge);
//        mBadge.setNumber(1);
        return inflater.inflate(R.layout.fragment_food_details, container, false);
    }
}
