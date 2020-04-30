package com.camm.foodizz.data.factory;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class QueryFactory {

    public static Query makeCategoryQuery(int start, int end){
        return FirebaseDatabase.getInstance().getReference("categories")
                .orderByChild("priority")
                .startAt(start)
                .endAt(end);
    }

    public static Query makeFoodQuery(double start, double end){
        return FirebaseDatabase.getInstance().getReference("foods")
                .orderByChild("totalScore")
                .startAt(start)
                .endAt(end);
    }

    public static Query makeRestaurantQuery(double start, double end){
        return FirebaseDatabase.getInstance().getReference("restaurants")
                .orderByChild("totalScore")
                .startAt(start)
                .endAt(end);
    }

}
