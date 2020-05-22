package com.camm.foodizz.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.camm.foodizz.data.room.CartRoomDatabase;
import com.camm.foodizz.data.room.dao.CartDao;
import com.camm.foodizz.data.room.entity.CartFood;

import java.util.List;

public class CartRepository {

    private CartDao cartDao;

    public CartRepository(Application application){
        CartRoomDatabase db = CartRoomDatabase.getDatabase(application);
        cartDao = db.cartDao();
    }

    public LiveData<List<CartFood>> getCart(){
        return cartDao.getCart();
    }

    public void insert(final CartFood food){
        CartRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.insert(food);
            }
        });
    }

    public void delete(final CartFood food){
        CartRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.delete(food);
            }
        });
    }

    public void updateQuantity(final String fId, final int newQuantity){
        CartRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updateQuantity(fId, newQuantity);
            }
        });
    }

    public void deleteCart(){
        CartRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteCart();
            }
        });
    }

}
