package com.camm.foodizz.restaurant;

import android.content.Context;
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
import com.camm.foodizz.models.FoodListDivider;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class RestaurantMenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewRestaurantMenu);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager managerMenu = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(managerMenu);

        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
        adapter.add(new FoodItem());
        adapter.add(new FoodItem());

        recyclerView.addItemDecoration(new FoodListDivider(LinearLayoutManager.VERTICAL, 0, 0, 0, 30));

        recyclerView.setAdapter(adapter);

        return view;
    }

    class FoodItem extends Item<GroupieViewHolder> {

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {

        }

        @Override
        public int getLayout() {
            return R.layout.raw_food_home;
        }
    }
}
