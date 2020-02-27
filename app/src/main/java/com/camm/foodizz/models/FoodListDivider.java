package com.camm.foodizz.models;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListDivider extends RecyclerView.ItemDecoration{
    private int mOrientation;

    public FoodListDivider(Context context, int orientation){
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0, 10, 0, 10);
        }
    }
}
