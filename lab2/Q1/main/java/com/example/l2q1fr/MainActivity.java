package com.example.l2q1fr;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        // Create a list of items
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("pls");
        itemList.add("work");
        itemList.add("PLEASE");
        itemList.add("Item 4");
        itemList.add("Item 5");

        // Set Adapter
        adapter = new MyRecyclerViewAdapter(itemList, new MyRecyclerViewAdapter.onClick() {
            @Override
            public void click(String str) {
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}