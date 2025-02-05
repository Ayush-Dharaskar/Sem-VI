package com.example.l4q1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView textView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);

        textView = findViewById(R.id.textView6);
        back = findViewById(R.id.button2);

        // Get the score from the Intent
        int score = getIntent().getIntExtra("score", 0);


        // Convert the score to a String and set it in the TextView
        textView.setText(Integer.toString(score));

        // Set onClickListener for the back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the ResultActivity and go back to MainActivity
            }
        });
    }

}
