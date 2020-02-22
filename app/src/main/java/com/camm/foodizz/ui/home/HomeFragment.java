package com.camm.foodizz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerCategory, recyclerFood, recyclerRestaurant;
    //private Button btnNotification;
    //private TextView txtNotification;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Mapping();
        manageRecycleView();
        setCategoryData();
        setFoodData();
        setRestaurantData();

        return view;
    }

    private void Mapping(){
        recyclerCategory = view.findViewById(R.id.recyclerViewHomeCategory);
        recyclerFood = view.findViewById(R.id.recyclerViewHomeFood);
        recyclerRestaurant = view.findViewById(R.id.recyclerViewHomeRestaurant);
        //btnNotification = view.findViewById(R.id.btnNotification);
        //txtNotification = view.findViewById(R.id.txtNotification);
    }

    private void manageRecycleView(){
        recyclerCategory.setHasFixedSize(true);
        LinearLayoutManager managerCategory = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerCategory.setLayoutManager(managerCategory);

        recyclerFood.setHasFixedSize(true);
        LinearLayoutManager managerFood = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerFood.setLayoutManager(managerFood);

        recyclerRestaurant.setHasFixedSize(true);
        LinearLayoutManager managerRestaurant = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerRestaurant.setLayoutManager(managerRestaurant);
    }

    private void setCategoryData(){
        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
        adapter.add(new CategoryItem("Fastfood", R.drawable.fastfood));
        adapter.add(new CategoryItem("Sushi", R.drawable.sushi));
        adapter.add(new CategoryItem("Pizza", R.drawable.pizza));
        adapter.add(new CategoryItem("Noodle", R.drawable.noodle));
        adapter.add(new CategoryItem("Hamburger", R.drawable.hamburger));
        adapter.add(new CategoryItem("Drinks", R.drawable.drinks));
        adapter.add(new CategoryItem("Hotdog", R.drawable.hotdog));
        adapter.add(new CategoryItem("Sausage", R.drawable.sausage));
        adapter.add(new CategoryItem("Sandwich", R.drawable.sandwich));
        adapter.add(new CategoryItem("Ice cream", R.drawable.ice_cream));
        adapter.add(new CategoryItem("Milk pack", R.drawable.milk_pack));
        adapter.add(new CategoryItem("Popcorn", R.drawable.popcorn));
        adapter.add(new CategoryItem("Kebab", R.drawable.kebab));
        adapter.add(new CategoryItem("Donut", R.drawable.donut));
        recyclerCategory.setAdapter(adapter);
    }

    private void setFoodData(){
        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
        adapter.add(new FoodItem());
        adapter.add(new FoodItem());
        recyclerFood.setAdapter(adapter);
    }

    private void setRestaurantData(){
        GroupAdapter adapter = new GroupAdapter<GroupieViewHolder>();
        adapter.add(new RestaurantItem());
        adapter.add(new RestaurantItem());
        recyclerRestaurant.setAdapter(adapter);
    }

    class CategoryItem extends Item<GroupieViewHolder>{

        private String categoryName;
        private int categoryImage;

        private CategoryItem(String categoryName, int categoryImage){
            this.categoryImage = categoryImage;
            this.categoryName = categoryName;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imgRawCategory = viewHolder.itemView.findViewById(R.id.imgRawCategory);
            TextView txtRawCategory = viewHolder.itemView.findViewById(R.id.txtRawCategory);

            imgRawCategory.setImageResource(categoryImage);
            txtRawCategory.setText(categoryName);
        }

        @Override
        public int getLayout() {
            return R.layout.raw_category;
        }
    }

    class FoodItem extends Item<GroupieViewHolder>{

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imgHomeFood1 = viewHolder.itemView.findViewById(R.id.imgHomeFood1);
            ImageView imgHomeFood2 = viewHolder.itemView.findViewById(R.id.imgHomeFood2);
            imgHomeFood1.setImageResource(R.drawable.dm2);
            imgHomeFood2.setImageResource(R.drawable.dm1);
        }

        @Override
        public int getLayout() {
            return R.layout.raw_food_home;
        }
    }

    class RestaurantItem extends Item<GroupieViewHolder>{

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imgRestaurantLogoHome = viewHolder.itemView.findViewById(R.id.imgRestaurantLogoHome);
            imgRestaurantLogoHome.setImageResource(R.drawable.domino);
        }

        @Override
        public int getLayout() {
            return R.layout.raw_restaurant_home;
        }
    }
}