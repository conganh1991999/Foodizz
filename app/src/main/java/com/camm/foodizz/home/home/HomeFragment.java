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
import com.camm.foodizz.food_detail.FoodReviewFragment;
import com.camm.foodizz.models.adapter.CategoryAdapter;
import com.camm.foodizz.models.data.CategoryData;
import com.camm.foodizz.models.adapter.FoodAdapter;
import com.camm.foodizz.models.data.FoodData;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.models.adapter.RestaurantAdapter;
import com.camm.foodizz.models.data.RestaurantData;
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

    private ArrayList<RestaurantData> listRestaurant;
    private RestaurantAdapter restaurantAdapter;
    private boolean isScrollingRestaurant = false;
    private double startRestaurant = 5.0, endRestaurant = 5.0;

    private ArrayList<FoodData> listFood;
    private FoodAdapter foodAdapter;
    private boolean isScrollingFood = false;
    private double startFood = 5.0, endFood = 5.0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Mapping();
        manageRecycleView();

        preloadCategoryData();
        initScrollCategoryListener();

        preloadRestaurantData();
        initScrollRestaurantListener();

        preloadFoodData();
        initScrollFoodListener();

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
        // Causes the Runnable object to be added to the message queue, to be run after the specified amount of time elapses.
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

    private void preloadRestaurantData(){
        listRestaurant = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(listRestaurant, getContext());
        recyclerRestaurant.addItemDecoration(new FoodListDivider(0, 0, 0, 12));
        recyclerRestaurant.setAdapter(restaurantAdapter);

        Query ref = FirebaseDatabase.getInstance().getReference("restaurants")
                .orderByChild("totalScore")
                .startAt(startRestaurant)
                .endAt(endRestaurant);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ArrayList<String> categoryName = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    categoryName.add(dataSnapshot.child("categoryName").child(String.valueOf(i)).getValue(String.class));
                }
                RestaurantData data = new RestaurantData(
                        dataSnapshot.getKey(),
                        dataSnapshot.child("name").getValue(String.class),
                        dataSnapshot.child("logo").getValue(String.class),
                        dataSnapshot.child("image").getValue(String.class),
                        categoryName,
                        dataSnapshot.child("totalScore").getValue(Double.class),
                        dataSnapshot.child("numOfRate").getValue(Integer.class));
                listRestaurant.add(data);
                restaurantAdapter.notifyDataSetChanged();
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

    private void initScrollRestaurantListener(){
        recyclerRestaurant.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isScrollingRestaurant) {
                    if (restaurantManager != null && restaurantManager.findLastCompletelyVisibleItemPosition() == listRestaurant.size() - 1) {
                        //bottom of list!
                        loadMoreRestaurant();
                        isScrollingRestaurant = true;
                    }
                }
            }
        });
    }

    private void loadMoreRestaurant(){
        startRestaurant -= 0.1; endRestaurant -= 0.1;

        listRestaurant.add(null);

        restaurantAdapter.notifyItemInserted(listRestaurant.size() - 1);

        Handler handler = new Handler();
        // Causes the Runnable object to be added to the message queue, to be run after the specified amount of time elapses.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listRestaurant.remove(listRestaurant.size() - 1);
                int scrollPosition = listRestaurant.size();
                restaurantAdapter.notifyItemRemoved(scrollPosition);

                Query ref = FirebaseDatabase.getInstance().getReference("restaurants")
                        .orderByChild("totalScore")
                        .startAt(startRestaurant)
                        .endAt(endRestaurant);
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ArrayList<String> categoryName = new ArrayList<>();
                        for(int i = 0; i < 3; i++){
                            categoryName.add(dataSnapshot.child("categoryName").child(String.valueOf(i)).getValue(String.class));
                        }
                        RestaurantData data = new RestaurantData(
                                dataSnapshot.getKey(),
                                dataSnapshot.child("name").getValue(String.class),
                                dataSnapshot.child("logo").getValue(String.class),
                                dataSnapshot.child("image").getValue(String.class),
                                categoryName,
                                dataSnapshot.child("totalScore").getValue(Double.class),
                                dataSnapshot.child("numOfRate").getValue(Integer.class));
                        listRestaurant.add(data);
                        restaurantAdapter.notifyDataSetChanged();
                        isScrollingRestaurant = false;
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

    private void preloadFoodData(){
        listFood = new ArrayList<>();
        foodAdapter = new FoodAdapter(listFood, getContext());
        recyclerFood.addItemDecoration(new FoodListDivider(0, 0, 30, 0));
        recyclerFood.setAdapter(foodAdapter);

        Query ref = FirebaseDatabase.getInstance().getReference("foods")
                .orderByChild("totalScore")
                .startAt(startFood)
                .endAt(endFood);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FoodData data = new FoodData(
                        dataSnapshot.getKey(),
                        dataSnapshot.child("name").getValue(String.class),
                        dataSnapshot.child("price").getValue(Double.class),
                        dataSnapshot.child("squareImage").getValue(String.class),
                        dataSnapshot.child("totalScore").getValue(Double.class),
                        dataSnapshot.child("numOfRate").getValue(Integer.class));
                listFood.add(data);
                foodAdapter.notifyDataSetChanged();
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

    private void initScrollFoodListener(){
        recyclerFood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isScrollingFood) {
                    if (foodManager != null && foodManager.findLastCompletelyVisibleItemPosition() == listFood.size() - 1) {
                        //bottom of list!
                        loadMoreFood();
                        isScrollingFood = true;
                    }
                }
            }
        });
    }

    private void loadMoreFood(){
        startFood -= 0.1; endFood -= 0.1;

        listFood.add(null);

        foodAdapter.notifyItemInserted(listFood.size() - 1);

        Handler handler = new Handler();
        // Causes the Runnable object to be added to the message queue, to be run after the specified amount of time elapses.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listFood.remove(listFood.size() - 1);
                int scrollPosition = listFood.size();
                foodAdapter.notifyItemRemoved(scrollPosition);

                Query ref = FirebaseDatabase.getInstance().getReference("foods")
                        .orderByChild("totalScore")
                        .startAt(startFood)
                        .endAt(endFood);
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        FoodData data = new FoodData(
                                dataSnapshot.getKey(),
                                dataSnapshot.child("name").getValue(String.class),
                                dataSnapshot.child("price").getValue(Double.class),
                                dataSnapshot.child("squareImage").getValue(String.class),
                                dataSnapshot.child("totalScore").getValue(Double.class),
                                dataSnapshot.child("numOfRate").getValue(Integer.class));
                        listFood.add(data);
                        foodAdapter.notifyDataSetChanged();
                        isScrollingFood = false;
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