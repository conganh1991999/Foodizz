package com.camm.foodizz.models;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDivider extends RecyclerView.ItemDecoration{
    private Drawable mDivider;
    private int mOrientation;

    public ItemDivider(Context context, int orientation){
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0, 0, 0, 30);
        }
    }
}
