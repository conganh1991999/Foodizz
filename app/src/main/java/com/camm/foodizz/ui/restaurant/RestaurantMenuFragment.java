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
import com.camm.foodizz.models.Food;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.ui.food_detail.FoodDetailViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuFragment extends Fragment {

    private static final String TAG = "RestaurantMenuFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        List<Food> listFoodsOnMenu = new ArrayList<>();

        if(getActivity() != null) {
            RestaurantViewModel restaurantModel = new ViewModelProvider(getActivity()).get(RestaurantViewModel.class);
            String restaurantId = restaurantModel.getRestaurantId();
            DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants")
                    .child(restaurantId).child("foods");
            restaurantRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String foodId = dataSnapshot.getValue(String.class);
                    Log.d(TAG,"foodId = " + foodId);
                    if(foodId != null){
                        DatabaseReference foodRef = FirebaseDatabase.getInstance().getReference("foods")
                                .child(foodId);
                        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d(TAG,"foodName = " + dataSnapshot.child("name").getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
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

        RecyclerView recyclerViewMenu = view.findViewById(R.id.recyclerViewRestaurantMenu);
        recyclerViewMenu.setHasFixedSize(true);
        GridLayoutManager menuManager = new GridLayoutManager(getContext(), 2);
        recyclerViewMenu.setLayoutManager(menuManager);

        // recyclerViewMenu.addItemDecoration(new FoodListDivider(0, 0, 0, 30));


        return view;
    }

}
