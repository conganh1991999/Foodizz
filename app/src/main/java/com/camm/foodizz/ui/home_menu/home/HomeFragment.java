package com.camm.foodizz.ui.home_menu.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.Category;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.Restaurant;
import com.camm.foodizz.models.adapter.CategoryAdapter;
import com.camm.foodizz.models.adapter.FoodAdapter;
import com.camm.foodizz.models.adapter.RestaurantAdapter;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.data.listener.ListCategoryListener;
import com.camm.foodizz.data.listener.ListFoodListener;
import com.camm.foodizz.data.listener.ListRestaurantListener;
import com.camm.foodizz.ui.food_detail.FoodDetailActivity;
import com.camm.foodizz.ui.restaurant.RestaurantActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

// TODO: using viewmodel for caching data
public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerCategory, recyclerFood, recyclerRestaurant;
    private LinearLayoutManager categoryManager, foodManager, restaurantManager;

    private ArrayList<Category> listCategory;
    private ArrayList<Restaurant> listRestaurant;
    private ArrayList<Food> listFood;

    private CategoryAdapter categoryAdapter;
    private RestaurantAdapter restaurantAdapter;
    private FoodAdapter foodAdapter;

    public static boolean isScrollingCategory = false, isScrollingRestaurant = false, isScrollingFood = false;
    public static String nextCategoryItemKey = null, nextRestaurantItemKey = null, nextFoodItemKey = null;

    private Query categoryRef, restaurantRef, foodRef;
    private ChildEventListener categoryListener, restaurantListener, foodListener;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        manageRecyclerViews();

        listCategory = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(listCategory, getContext());
        recyclerCategory.addItemDecoration(new FoodListDivider(0, 0, 30, 0));
        recyclerCategory.setAdapter(categoryAdapter);

        listRestaurant = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(listRestaurant, getContext());
        recyclerRestaurant.addItemDecoration(new FoodListDivider(0, 0, 0, 12));
        recyclerRestaurant.setAdapter(restaurantAdapter);

        listFood = new ArrayList<>();
        foodAdapter = new FoodAdapter(listFood, getContext());
        recyclerFood.addItemDecoration(new FoodListDivider(0, 0, 30, 0));
        recyclerFood.setAdapter(foodAdapter);

        initItemClickListener();

        // TODO: force user to wait for populating data
        preloadCategoryData();
        preloadRestaurantData();
        preloadFoodData();

        initScrollCategoryListener();
        initScrollRestaurantListener();
        initScrollFoodListener();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isScrollingCategory = false;
        isScrollingRestaurant = false;
        isScrollingFood = false;
        nextCategoryItemKey = null;
        nextRestaurantItemKey = null;
        nextFoodItemKey = null;
        categoryRef.removeEventListener(categoryListener);
        foodRef.removeEventListener(foodListener);
        restaurantRef.removeEventListener(restaurantListener);
    }

    private void manageRecyclerViews() {
        recyclerCategory = view.findViewById(R.id.recyclerHomeCategory);
        recyclerFood = view.findViewById(R.id.recyclerHomeFood);
        recyclerRestaurant = view.findViewById(R.id.recyclerHomeRestaurant);

        categoryManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        foodManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        restaurantManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerCategory.setHasFixedSize(true);
        recyclerCategory.setLayoutManager(categoryManager);

        recyclerFood.setHasFixedSize(true);
        recyclerFood.setLayoutManager(foodManager);

        recyclerRestaurant.setHasFixedSize(true);
        recyclerRestaurant.setLayoutManager(restaurantManager);
    }

    private void initItemClickListener() {
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("foodId", food.getFoodId());
                startActivity(intent);
            }
        });

        restaurantAdapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                Intent intent = new Intent(getContext(), RestaurantActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
        });
    }

    private void preloadCategoryData() {

        categoryRef = FirebaseDatabase.getInstance().getReference("categories")
                .orderByKey()
                .limitToFirst(5);

        categoryListener = new ListCategoryListener(listCategory, categoryAdapter);

        categoryRef.addChildEventListener(categoryListener);

    }

    private void preloadFoodData() {

        foodRef = FirebaseDatabase.getInstance().getReference("foods")
                .orderByKey()
                .limitToFirst(5);

        foodListener = new ListFoodListener(listFood, foodAdapter);

        foodRef.addChildEventListener(foodListener);

    }

    private void preloadRestaurantData() {

        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants")
                .orderByKey()
                .limitToFirst(5);

        restaurantListener = new ListRestaurantListener(listRestaurant, restaurantAdapter);

        restaurantRef.addChildEventListener(restaurantListener);

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
                if (isScrollingCategory) {
                    if (categoryManager != null &&
                            categoryManager.findLastCompletelyVisibleItemPosition() == listCategory.size() - 1) {
                        loadMoreCategory();
                        isScrollingCategory = false;
                    }
                }
            }
        });
    }

    private void initScrollRestaurantListener() {
        recyclerRestaurant.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollingRestaurant) {
                    if (restaurantManager != null &&
                            restaurantManager.findLastCompletelyVisibleItemPosition() == listRestaurant.size() - 1) {
                        loadMoreRestaurant();
                        isScrollingRestaurant = false;
                    }
                }
            }
        });
    }

    private void initScrollFoodListener() {
        recyclerFood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollingFood) {
                    if (foodManager != null &&
                            foodManager.findLastCompletelyVisibleItemPosition() == listFood.size() - 1) {
                        loadMoreFood();
                        isScrollingFood = false;
                    }
                }
            }
        });
    }

    private void loadMoreCategory() {

        categoryRef.removeEventListener(categoryListener);
        categoryRef = FirebaseDatabase.getInstance().getReference("categories")
                .orderByKey()
                .startAt(nextCategoryItemKey)
                .limitToFirst(5);

        categoryListener = new ListCategoryListener(listCategory, categoryAdapter);

        categoryRef.addChildEventListener(categoryListener);

    }

    private void loadMoreFood() {

        foodRef.removeEventListener(foodListener);
        foodRef = FirebaseDatabase.getInstance().getReference("foods")
                .orderByKey()
                .startAt(nextFoodItemKey)
                .limitToFirst(5);

        foodListener = new ListFoodListener(listFood, foodAdapter);

        foodRef.addChildEventListener(foodListener);

    }

    private void loadMoreRestaurant() {

        restaurantRef.removeEventListener(restaurantListener);
        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants")
                .orderByKey()
                .startAt(nextRestaurantItemKey)
                .limitToFirst(5);

        restaurantListener = new ListRestaurantListener(listRestaurant, restaurantAdapter);

        restaurantRef.addChildEventListener(restaurantListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryRef.removeEventListener(categoryListener);
        foodRef.removeEventListener(foodListener);
        restaurantRef.removeEventListener(restaurantListener);
    }

}
