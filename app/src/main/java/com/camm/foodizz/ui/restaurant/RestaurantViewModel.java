package com.camm.foodizz.ui.restaurant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class RestaurantViewModel extends AndroidViewModel {

    private String restaurantId;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
    }

}
