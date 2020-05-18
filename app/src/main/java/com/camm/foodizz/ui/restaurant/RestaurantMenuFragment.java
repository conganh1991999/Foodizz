package com.camm.foodizz.ui.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.models.decorator.FoodListDivider;

public class RestaurantMenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

//        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewRestaurantMenu);
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager managerMenu = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(managerMenu);
//
//        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
//        adapter.add(new FoodItem());
//        adapter.add(new FoodItem());
//
//        recyclerView.addItemDecoration(new FoodListDivider(0, 0, 0, 30));
//
//        recyclerView.setAdapter(adapter);

        return view;
    }

}
