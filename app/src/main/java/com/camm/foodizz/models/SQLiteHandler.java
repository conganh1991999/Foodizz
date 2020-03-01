package com.camm.foodizz.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHandler extends SQLiteOpenHelper {

    public SQLiteHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String TABLE_LOGIN = "Login";
    private static final String ID = "Id";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s VARCHAR, %s VARCHAR)",
                TABLE_LOGIN, ID, EMAIL, PASSWORD);
        db.execSQL(sqlTable);
        db.execSQL("INSERT INTO Login VALUES (0, '', '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateLoginInfo(String email, String password){
        this.getWritableDatabase().execSQL("DELETE FROM Login");
        this.getWritableDatabase().execSQL(String.format("INSERT INTO Login VALUES (0, '%s', '%s')", email, password));
    }

    public Cursor getLoginData(){
        return this.getReadableDatabase().rawQuery("SELECT * FROM Login", null);
    }
}
