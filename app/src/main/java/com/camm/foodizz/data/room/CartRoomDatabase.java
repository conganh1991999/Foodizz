package com.camm.foodizz.data.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.camm.foodizz.data.room.dao.CartDao;
import com.camm.foodizz.data.room.entity.CartFood;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CartFood.class}, version = 1)
public abstract class CartRoomDatabase extends RoomDatabase {

    public abstract CartDao cartDao();

    private static volatile CartRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CartRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (CartRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CartRoomDatabase.class, "cart_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(roomCallBack)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };

}
