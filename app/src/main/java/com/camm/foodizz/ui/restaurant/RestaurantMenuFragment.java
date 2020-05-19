package com.camm.foodizz.ui.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.DataRemovalRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.listener.MenuItemListener;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.MenuItemAdapter;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.ui.food_detail.FoodDetailViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuFragment extends Fragment {

    private static final String TAG = "RestaurantMenuFragment";

    private View view;

    private ArrayList<Food> listFoodsOnMenu;
    private RecyclerView recyclerViewMenu;
    private GridLayoutManager menuManager;
    private MenuItemAdapter menuAdapter;

    private String restaurantId;
    private Query restaurantMenuRef;
    private ChildEventListener restaurantMenuListener;

    public static String nextMenuFoodItemKey = null;
    public static boolean isScrollingMenu = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        if(getActivity() != null) {
            RestaurantViewModel restaurantModel = new ViewModelProvider(getActivity()).get(RestaurantViewModel.class);
            restaurantId = restaurantModel.getRestaurantId();
        }

        manageRecyclerView();

        listFoodsOnMenu = new ArrayList<>();
        menuAdapter = new MenuItemAdapter(listFoodsOnMenu, getContext());
        recyclerViewMenu.addItemDecoration(new FoodListDivider(12, 0, 12, 24));
        recyclerViewMenu.setAdapter(menuAdapter);

        preloadMenuItem();
        initScrollMenuListener();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        nextMenuFoodItemKey = null;
        isScrollingMenu = false;
        if(restaurantMenuRef != null && restaurantMenuListener != null)
            restaurantMenuRef.removeEventListener(restaurantMenuListener);
    }

    private void manageRecyclerView(){
        recyclerViewMenu = view.findViewById(R.id.recyclerViewRestaurantMenu);
        menuManager = new GridLayoutManager(getContext(), 2);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(menuManager);
    }

    private void preloadMenuItem(){
        if(restaurantId != null){
            restaurantMenuRef = FirebaseDatabase.getInstance().getReference("restaurants")
                    .child(restaurantId).child("foods")
                    .orderByKey()
                    .limitToFirst(5);
            restaurantMenuListener = new MenuItemListener(listFoodsOnMenu, menuAdapter);
            restaurantMenuRef.addChildEventListener(restaurantMenuListener);
        }
    }

    private void initScrollMenuListener(){
        recyclerViewMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollingMenu) {
                    if (menuManager != null &&
                            menuManager.findLastCompletelyVisibleItemPosition() == listFoodsOnMenu.size() - 1) {
                        loadMoreMenu();
                        isScrollingMenu = false;
                    }
                }
            }
        });
    }

    private void loadMoreMenu(){
        restaurantMenuRef.removeEventListener(restaurantMenuListener);
        restaurantMenuRef = FirebaseDatabase.getInstance().getReference("restaurants")
                .child(restaurantId).child("foods")
                .orderByKey()
                .startAt(nextMenuFoodItemKey)
                .limitToFirst(5);
        restaurantMenuListener = new MenuItemListener(listFoodsOnMenu, menuAdapter);
        restaurantMenuRef.addChildEventListener(restaurantMenuListener);
    }

}
