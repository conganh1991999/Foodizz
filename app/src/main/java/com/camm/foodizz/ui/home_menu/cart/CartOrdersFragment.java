package com.camm.foodizz.ui.home_menu.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camm.foodizz.R;
import com.camm.foodizz.data.room.entity.CartFood;
import com.camm.foodizz.models.adapter.CartFoodAdapter;
import com.camm.foodizz.models.decorator.FoodListDivider;
import com.camm.foodizz.ui.food_detail.FoodDetailActivity;

import java.text.DecimalFormat;
import java.util.List;

public class CartOrdersFragment extends Fragment {

    private View view;

    private CartViewModel cartModel;

    private Button btnCheckout;

    private RecyclerView recyclerFoodsInCart;
    private CartFoodAdapter adapter;

    private double subTotal = 0.0;
    private double shippingFee = 15.0; // mock
    private double total = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart_orders, container, false);

        if(getActivity() != null) {
            cartModel = new ViewModelProvider(getActivity()).get(CartViewModel.class);
        }

        mapping();
        manageRecyclerView();

        adapter = new CartFoodAdapter();
        recyclerFoodsInCart.addItemDecoration(new FoodListDivider(0, 0, 0, 12));
        recyclerFoodsInCart.setAdapter(adapter);

        adapter.setOnItemClickListener(new CartFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String foodId, int quantity) {
                Intent intent = new Intent(getContext(), FoodDetailActivity.class);
                intent.putExtra("foodId", foodId);
                intent.putExtra("quantity", quantity);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cartModel.deleteFood(adapter.getCartFoodAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerFoodsInCart);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogCheckout();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cartModel.getCartOrders().observe(getViewLifecycleOwner(), new Observer<List<CartFood>>() {
            @Override
            public void onChanged(List<CartFood> cartFoods) {
                adapter.submitList(cartFoods);
                subTotal = 0.0;
                updateTotal(cartFoods);
            }
        });
    }

    private void mapping() {
        recyclerFoodsInCart = view.findViewById(R.id.recyclerFoodsOnCart);
        btnCheckout = view.findViewById(R.id.btnCheckOut);
    }

    private void manageRecyclerView() {
        recyclerFoodsInCart.setHasFixedSize(true);
        recyclerFoodsInCart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void updateTotal(List<CartFood> cartFoods){
        for (int i = 0; i < cartFoods.size(); i++) {
            subTotal += cartFoods.get(i).getFoodPrice()*cartFoods.get(i).getQuantity();
        }
        total = subTotal + shippingFee;
    }

    private void createDialogCheckout(){
        if(getContext() != null){
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_checkout);

            Button btnPay = dialog.findViewById(R.id.btnPayCheckout);
            TextView txtSubtotal = dialog.findViewById(R.id.txtSubtotal);
            TextView txtShippingFee = dialog.findViewById(R.id.txtShippingFee);
            TextView txtTotal = dialog.findViewById(R.id.txtTotal);

            DecimalFormat formatter = new DecimalFormat("######.00");
            txtSubtotal.setText(String.format("$%s", formatter.format(subTotal)));
            txtShippingFee.setText(String.format("$%s", formatter.format(shippingFee)));
            txtTotal.setText(String.format("$%s", formatter.format(total)));

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: payment module
                }
            });

            dialog.show();
        }
    }

}
