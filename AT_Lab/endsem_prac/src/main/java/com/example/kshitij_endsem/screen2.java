package com.example.kshitij_endsem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Currency;

public class screen2 extends AppCompatActivity {

    String selected;
    ListView lv,lv2;
    ArrayList<String> allres;
    ArrayAdapter<String> adapter;
    ArrayList<String> sum;
    ArrayAdapter<String> ada;
    DatabaseHelper db;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_2);
        db = new DatabaseHelper(this);
        lv= findViewById(R.id.listview);
        lv2 = findViewById(R.id.lv2);
        allres = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,allres);
        lv.setAdapter(adapter);
Intent intent = getIntent();
        selected = getIntent().getStringExtra("cu");
        Cursor cur = db.fetchRestaurantsByCuisine(selected);
        while(cur.moveToNext())
        {
            allres.add(cur.getString(1));
        }
        adapter.notifyDataSetChanged();

        sum = new ArrayList<>();
        ada = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,sum);
        lv2.setAdapter(ada);

        Cursor cur2 = db.fetchIdPhone();
        while(cur2.moveToNext())
        {
            long temp = Long.parseLong(cur2.getString(cur2.getColumnIndexOrThrow("id"))) * Long.parseLong(cur2.getString(1));
            sum.add(String.valueOf(temp));
        }
        ada.notifyDataSetChanged();

    }
}
