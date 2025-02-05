package com.example.l4q1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    RadioGroup rg1,rg2,rg3;
    Button submitButton;
    int score=0;
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

        rg1 = findViewById(R.id.radioGroup);
        rg2 = findViewById(R.id.radioGroup2);
        rg3 = findViewById(R.id.radioGroup3);
        submitButton = findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Submit Conformation")
                        .setMessage("Are you sure you want to submit?")
                        .setPositiveButton("Yes",(dialog, which) ->{
                            score=0;
                            int selectedID1 = rg1.getCheckedRadioButtonId();
                            RadioButton rb1 = findViewById(selectedID1);
                            if(rb1 != null && rb1.getText().toString().equals("Yes"))
                            {
                                score++;
                            }

                            int selectedId2 = rg2.getCheckedRadioButtonId();
                            RadioButton rb2 = findViewById(selectedId2);
                            if(rb2 !=null && rb2.getText().toString().equals("11"))
                            {
                                score++;
                            }
                            int selectedId3 = rg3.getCheckedRadioButtonId();
                            RadioButton rb3 = findViewById(selectedId3);
                            if(rb3 !=null && rb3.getText().toString().equals("Hammer"))
                            {
                                score++;
                            }
                            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                            intent.putExtra("score",score);
                            startActivity(intent);
                        })
                        .setNegativeButton("No",(dialog,which)->{
                            dialog.dismiss();
                        })
                        .create()
                        .show();
            }
        });
    }

}