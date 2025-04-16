package com.example.kshitij_endsem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.nio.DoubleBuffer;

public class DatabaseHelper extends SQLiteOpenHelper {
    String DB_NAME = "Endsem";
    String TABLE_NAME = "Restaurants";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Endsem", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Restaurants (id INTEGER PRIMARY KEY, name TEXT, phone TEXT,location TEXT, cuisine TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean addRestaurant(int id, String name, String phone, String location, String cuisine)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id); // manual id insert
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("location", location);
        cv.put("cuisine", cuisine);

        long result = db.insert(TABLE_NAME,null,cv);
        return result != -1;
    }
    public boolean delRestaurant(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
        return res != -1;
    }

    public Cursor fetchCuisine(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT DISTINCT cuisine from "+TABLE_NAME,null);
    }
    public Cursor fetchRestaurantsByCuisine(String cu){
     SQLiteDatabase db = this.getReadableDatabase();
     return db.rawQuery("Select * from "+ TABLE_NAME+" where cuisine = ? order by name",new
             String[]{cu});
    }

    public Cursor fetchIdPhone(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id,phone from "+ TABLE_NAME,null);
    }




}
