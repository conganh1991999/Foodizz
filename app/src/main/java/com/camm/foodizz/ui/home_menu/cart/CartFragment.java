package com.camm.foodizz.ui.home_menu.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.camm.foodizz.R;
import com.camm.foodizz.models.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private View view;

    private TabLayout cartTabLayout;
    private ViewPager cartFragmentPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        mapping();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new CartOrdersFragment());
        fragmentList.add(new PaidOrdersFragment());
        fragmentList.add(new DoneOrdersFragment());

        List<String> titleList = new ArrayList<>();
        titleList.add("Foods Cart");
        titleList.add("Paid Orders");
        titleList.add("Done Orders");

        if(getActivity() != null){
            PagerAdapter cartPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 1);
            cartPagerAdapter.addFragment(fragmentList, titleList);
            cartFragmentPager.setAdapter(cartPagerAdapter);
            cartTabLayout.setupWithViewPager(cartFragmentPager);
        }

        return view;
    }

    private void mapping(){
        cartTabLayout = view.findViewById(R.id.cartTabLayout);
        cartFragmentPager = view.findViewById(R.id.cartFragmentPager);
    }

}
