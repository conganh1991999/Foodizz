package com.camm.foodizz.ui;

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
import com.camm.foodizz.models.Divider;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class RestaurantReviewFragment extends Fragment {

    private RecyclerView recyclerViewRestaurantReview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_reviews, container, false);

        recyclerViewRestaurantReview = view.findViewById(R.id.recyclerViewRestaurantReview);
        recyclerViewRestaurantReview.setHasFixedSize(true);
        LinearLayoutManager managerReview = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRestaurantReview.setLayoutManager(managerReview);

        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
        adapter.add(new ReviewItem());
        adapter.add(new ReviewItem());
        adapter.add(new ReviewItem());

        Context context = getContext();
        if(context != null){
            recyclerViewRestaurantReview.addItemDecoration(new Divider(context, LinearLayoutManager.VERTICAL));
        }

        recyclerViewRestaurantReview.setAdapter(adapter);

        return view;
    }

    class ReviewItem extends Item<GroupieViewHolder> {

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {

        }

        @Override
        public int getLayout() {
            return R.layout.raw_restaurant_review;
        }
    }
}
