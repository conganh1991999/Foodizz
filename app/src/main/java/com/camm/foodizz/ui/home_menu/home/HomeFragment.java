package com.camm.foodizz.ui.home_menu.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.factory.QueryFactory;
import com.camm.foodizz.models.Category;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.Restaurant;
import com.camm.foodizz.models.adapter.CategoryAdapter;
import com.camm.foodizz.models.adapter.FoodAdapter;
import com.camm.foodizz.models.adapter.RestaurantAdapter;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.models.listener.CategoryListener;
import com.camm.foodizz.models.listener.FoodListener;
import com.camm.foodizz.models.listener.RestaurantListener;
import com.camm.foodizz.ui.food_detail.FoodDetailActivity;
import com.camm.foodizz.ui.restaurant.RestaurantActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;

import java.util.ArrayList;

// TODO: using viewmodel for caching data
public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerCategory, recyclerFood, recyclerRestaurant;
    private LinearLayoutManager categoryManager, foodManager, restaurantManager;

    private ArrayList<Category> listCategory;
    private CategoryAdapter categoryAdapter;
    public static boolean isScrollingCategory = false;
    private int startCategory = 0, endCategory = 5;

    private ArrayList<Restaurant> listRestaurant;
    private RestaurantAdapter restaurantAdapter;
    public static boolean isScrollingRestaurant = false;
    private double startRestaurant = 5.0, endRestaurant = 5.0;

    private ArrayList<Food> listFood;
    private FoodAdapter foodAdapter;
    public static boolean isScrollingFood = false;
    private double startFood = 5.0, endFood = 5.0;

    private Handler handler = new Handler();

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

    private void manageRecyclerViews(){
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

    private void initItemClickListener(){
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("food", food);
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

    private void preloadCategoryData(){

        Query categoryRef = QueryFactory.makeCategoryQuery(startCategory, endCategory);

        ChildEventListener categoryListener = new CategoryListener(listCategory, categoryAdapter);

        categoryRef.addChildEventListener(categoryListener);
    }

    private void preloadFoodData(){

        Query foodRef = QueryFactory.makeFoodQuery(startFood, endFood);

        ChildEventListener foodListener = new FoodListener(listFood, foodAdapter);

        foodRef.addChildEventListener(foodListener);

    }

    private void preloadRestaurantData(){

        Query RestaurantRef = QueryFactory.makeRestaurantQuery(startRestaurant, endRestaurant);

        ChildEventListener restaurantListener = new RestaurantListener(listRestaurant, restaurantAdapter);

        RestaurantRef.addChildEventListener(restaurantListener);

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
                    if (categoryManager != null &&
                            categoryManager.findLastCompletelyVisibleItemPosition() == listCategory.size() - 1) {
                        loadMoreCategory();
                        isScrollingCategory = true;
                    }
                }
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
                    if (restaurantManager != null &&
                            restaurantManager.findLastCompletelyVisibleItemPosition() == listRestaurant.size() - 1) {
                        loadMoreRestaurant();
                        isScrollingRestaurant = true;
                    }
                }
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
                    if (foodManager != null &&
                            foodManager.findLastCompletelyVisibleItemPosition() == listFood.size() - 1) {
                        loadMoreFood();
                        isScrollingFood = true;
                    }
                }
            }
        });
    }

    private void loadMoreCategory() {
        // TODO: paging efficiently
        startCategory += 6; endCategory += 6;

        listCategory.add(null);
        categoryAdapter.notifyItemInserted(listCategory.size() - 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listCategory.remove(listCategory.size() - 1);
                categoryAdapter.notifyItemRemoved(listCategory.size());

                Query categoryRef = QueryFactory.makeCategoryQuery(startCategory, endCategory);

                ChildEventListener categoryListener = new CategoryListener(listCategory, categoryAdapter);

                categoryRef.addChildEventListener(categoryListener);

            }
        }, 2000);
    }

    private void loadMoreFood(){
        // TODO: do paging efficiently
        startFood -= 0.1; endFood -= 0.1;

        listFood.add(null);
        foodAdapter.notifyItemInserted(listFood.size() - 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listFood.remove(listFood.size() - 1);
                foodAdapter.notifyItemRemoved(listFood.size());

                Query ref = QueryFactory.makeFoodQuery(startFood, endFood);

                ChildEventListener foodListener = new FoodListener(listFood, foodAdapter);

                ref.addChildEventListener(foodListener);

            }
        }, 2000);
    }

    private void loadMoreRestaurant(){
        // TODO: do paging efficiently
        startRestaurant -= 0.1; endRestaurant -= 0.1;

        listRestaurant.add(null);
        restaurantAdapter.notifyItemInserted(listRestaurant.size() - 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listRestaurant.remove(listRestaurant.size() - 1);
                restaurantAdapter.notifyItemRemoved(listRestaurant.size());

                Query RestaurantRef = QueryFactory.makeRestaurantQuery(startRestaurant, endRestaurant);

                ChildEventListener restaurantListener = new RestaurantListener(listRestaurant, restaurantAdapter);

                RestaurantRef.addChildEventListener(restaurantListener);
            }
        }, 2000);
    }

}
