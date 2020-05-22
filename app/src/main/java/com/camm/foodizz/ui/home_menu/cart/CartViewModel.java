package com.camm.foodizz.ui.home_menu.cart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.camm.foodizz.data.repository.CartRepository;
import com.camm.foodizz.data.room.entity.CartFood;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepository cartRepository;

    private LiveData<List<CartFood>> cartOrders;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
        cartOrders = cartRepository.getCart();
    }

    LiveData<List<CartFood>> getCartOrders(){
        return cartOrders;
    }

    void deleteFood(CartFood food){
        cartRepository.delete(food);
    }

    void deleteAllFoods(){
        cartRepository.deleteCart();
    }

}