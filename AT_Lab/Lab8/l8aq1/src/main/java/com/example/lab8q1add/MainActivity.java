package com.example.lab8q1add;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etBudget, etLimit;
    Button btnSetBudget, btnGoExpense, btnSetReminder;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBudget = findViewById(R.id.etBudget);
        etLimit = findViewById(R.id.etLimit);
        btnSetBudget = findViewById(R.id.btnSetBudget);
        btnGoExpense = findViewById(R.id.btnGoExpense);
        btnSetReminder = findViewById(R.id.btnSetReminder);
        db = new DBHelper(this);

        btnSetBudget.setOnClickListener(v -> {
            String budget = etBudget.getText().toString();
            String limit = etLimit.getText().toString();
            db.setBudget(budget, limit);
            Toast.makeText(this, "Budget Set!", Toast.LENGTH_SHORT).show();
        });

        btnGoExpense.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
        });

        btnSetReminder.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 10); // test after 10 seconds

            Intent intent = new Intent(this, ReminderReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Toast.makeText(this, "Reminder Set (10s)", Toast.LENGTH_SHORT).show();
        });
    }
}
