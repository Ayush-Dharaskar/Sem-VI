package com.example.l2q3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Spinner spin;
    private View mainLayout;
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

        spin = findViewById(R.id.spinner);
        mainLayout = findViewById(R.id.main);

        String[] colors = {"Select Color", "Red", "Green", "Blue", "Yellow", "Gray"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 1:
                        mainLayout.setBackgroundColor(Color.RED);
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        break;
                    case 2:
                        mainLayout.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        mainLayout.setBackgroundColor(Color.BLUE);
                        break;
                    case 4:
                        mainLayout.setBackgroundColor(Color.YELLOW);
                        break;
                    case 5:
                        mainLayout.setBackgroundColor(Color.GRAY);
                        break;
                    default:
                        mainLayout.setBackgroundColor(Color.WHITE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}