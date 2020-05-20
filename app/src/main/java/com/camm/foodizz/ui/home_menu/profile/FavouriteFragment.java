package com.camm.foodizz.ui.home_menu.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.listener.MenuItemListener;
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.adapter.MenuItemAdapter;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    private View view;

    private ArrayList<Food> listFoodsOnMenu;
    private RecyclerView recyclerViewMenu;
    private GridLayoutManager menuManager;
    private MenuItemAdapter menuAdapter;

    private String myId;
    private Query restaurantMenuRef;
    private ChildEventListener restaurantMenuListener;

    public static String nextMenuFoodItemKey = null;
    public static boolean isScrollingMenu = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_favourite, container, false);

        getCurrentUserId();

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

    private void getCurrentUserId(){
        FirebaseUser myInfo = FirebaseAuth.getInstance().getCurrentUser();
        if(myInfo != null) {
            myId = myInfo.getUid();
        }
    }

    private void manageRecyclerView(){
        recyclerViewMenu = view.findViewById(R.id.recyclerViewFavourite);
        menuManager = new GridLayoutManager(getContext(), 2);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(menuManager);
    }

    private void preloadMenuItem(){
        if(myId != null){
            restaurantMenuRef = FirebaseDatabase.getInstance().getReference("users")
                    .child(myId).child("foods")
                    .orderByKey()
                    .limitToFirst(5);
            restaurantMenuListener = new MenuItemListener(listFoodsOnMenu, menuAdapter, "FavouriteMenu");
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
        restaurantMenuRef = FirebaseDatabase.getInstance().getReference("users")
                .child(myId).child("foods")
                .orderByKey()
                .startAt(nextMenuFoodItemKey)
                .limitToFirst(5);
        restaurantMenuListener = new MenuItemListener(listFoodsOnMenu, menuAdapter, "FavouriteMenu");
        restaurantMenuRef.addChildEventListener(restaurantMenuListener);
    }

}
