package com.camm.foodizz.home.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.CategoryAdapter;
import com.camm.foodizz.models.CategoryData;
import com.camm.foodizz.pushdata.PushCategoryData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerCategory, recyclerFood, recyclerRestaurant;
    private LinearLayoutManager categoryManager, foodManager, restaurantManager;

    private ArrayList<CategoryData> listCategory;
    private CategoryAdapter categoryAdapter;

    private boolean isScrollingCategory = false;
    private int startCategory = 0, endCategory = 5;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Mapping();
        manageRecycleView();

        preloadCategoryData();
        initScrollCategoryListener();

        return view;
    }

    private void Mapping(){
        recyclerCategory = view.findViewById(R.id.recyclerHomeCategory);
        recyclerFood = view.findViewById(R.id.recyclerHomeFood);
        recyclerRestaurant = view.findViewById(R.id.recyclerHomeRestaurant);
    }

    private void manageRecycleView(){
        recyclerCategory.setHasFixedSize(true);
        categoryManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerCategory.setLayoutManager(categoryManager);

        recyclerFood.setHasFixedSize(true);
        foodManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerFood.setLayoutManager(foodManager);

        recyclerRestaurant.setHasFixedSize(true);
        restaurantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerRestaurant.setLayoutManager(restaurantManager);
    }

    private void preloadCategoryData(){
        listCategory = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(listCategory, getContext());
        recyclerCategory.setAdapter(categoryAdapter);

        Query ref = FirebaseDatabase.getInstance().getReference("categories")
                .orderByChild("priority")
                .startAt(startCategory)
                .endAt(endCategory);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                CategoryData data = new CategoryData(
                        dataSnapshot.child("name").getValue(String.class),
                        dataSnapshot.child("image").getValue(String.class));
                listCategory.add(data);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initScrollCategoryListener() {
        recyclerCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isScrollingCategory) {
                    if (categoryManager != null && categoryManager.findLastCompletelyVisibleItemPosition() == listCategory.size() - 1) {
                        //bottom of list!
                        loadMoreCategory();
                        isScrollingCategory = true;
                    }
                }
            }
        });
    }

    private void loadMoreCategory() {
        startCategory += 6; endCategory += 6;

        listCategory.add(null);

        categoryAdapter.notifyItemInserted(listCategory.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listCategory.remove(listCategory.size() - 1);
                int scrollPosition = listCategory.size();
                categoryAdapter.notifyItemRemoved(scrollPosition);

                Query ref = FirebaseDatabase.getInstance().getReference("categories")
                        .orderByChild("priority")
                        .startAt(startCategory)
                        .endAt(endCategory);
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        CategoryData data = new CategoryData(
                                dataSnapshot.child("name").getValue(String.class),
                                dataSnapshot.child("image").getValue(String.class));
                        listCategory.add(data);
                        categoryAdapter.notifyDataSetChanged();
                        isScrollingCategory = false;
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }, 2000);
    }

}