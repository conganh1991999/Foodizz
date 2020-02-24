package com.camm.foodizz.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.camm.foodizz.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> colorName;

    public SliderAdapter(Context context, ArrayList<String> colorName) {
        this.context = context;
        this.colorName = colorName;
    }

    @Override
    public int getCount() {
        return colorName.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.item_slider, container, false);

        TextView textView = view.findViewById(R.id.textViewDemo);
        textView.setText(colorName.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
