package com.camm.foodizz.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.camm.foodizz.data.room.entity.CartFood;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartFood food);

    @Delete
    void delete(CartFood food);

    @Query("UPDATE cart_table SET quantity = :newQuantity WHERE foodId = :fId")
    void updateQuantity(String fId, int newQuantity);

    @Query("DELETE FROM cart_table")
    void deleteCart();

    @Query("SELECT * FROM cart_table ORDER BY foodId ASC")
    LiveData<List<CartFood>> getCart();

}
