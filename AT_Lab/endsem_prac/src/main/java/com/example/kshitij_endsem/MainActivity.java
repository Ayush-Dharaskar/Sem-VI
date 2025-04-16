package com.example.kshitij_endsem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText eid,ename,ephone,elocation,ecuisine;
    Button add,del,next;
    Spinner spin;
    DatabaseHelper db;
    ArrayAdapter<String> adapter;
    ArrayList<String> reslest;
    String selected;
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


        db = new DatabaseHelper(this);
        eid = findViewById(R.id.editTextNumber);
        ename = findViewById(R.id.editTextText);
        ephone = findViewById(R.id.editTextPhone);
        elocation = findViewById(R.id.editTextText2);
        ecuisine = findViewById(R.id.editTextText3);
        Button add = findViewById(R.id.button);
        Button del = findViewById(R.id.button2);
        Button next = findViewById(R.id.button3);
        Spinner spin = findViewById(R.id.spinner);

        reslest = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,reslest);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        refreshSpinner();
        add.setOnClickListener(v->{
            String id = eid.getText().toString();
            String name = ename.getText().toString();
            String phone = ephone.getText().toString();
            String location = elocation.getText().toString();
            String cuisine = ecuisine.getText().toString();

            if(id.isEmpty() || name.isEmpty() || phone.isEmpty() || location.isEmpty() || cuisine.isEmpty())
            {
                showToast("FILL ALL FIELDS");
                return;
            }
            if(phone.length() != 10)
            {
                showToast("only 10 digits");
                return;
            }


            boolean ins = db.addRestaurant(Integer.parseInt(id),name,phone,location,cuisine);
            showToast(ins ? "Added":"failed");
            refreshSpinner();
        });

        del.setOnClickListener(v->{
            String id = eid.getText().toString();
            boolean dele = db.delRestaurant(Integer.parseInt(id));
            showToast(dele ? "deleted":"failed");
            refreshSpinner();
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = reslest.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = "";
            }
        });
        next.setOnClickListener(v->{
            if(selected.isEmpty())
            {
                showToast("SELECT SMTHG");
                return;
            }
            Intent intent = new Intent(MainActivity.this, screen2.class);
            intent.putExtra("cu",selected);
            startActivity(intent);

        });


    }

    public void refreshSpinner(){
        reslest.clear();
        Cursor cur= db.fetchCuisine();
        while(cur.moveToNext())
        {
            reslest.add(cur.getString(0));

        }
        adapter.notifyDataSetChanged();
    }
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}