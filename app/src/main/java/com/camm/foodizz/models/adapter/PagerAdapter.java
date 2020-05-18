package com.camm.foodizz.models.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public void addFragment(List<Fragment> fragmentList, List<String> titleList){
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
