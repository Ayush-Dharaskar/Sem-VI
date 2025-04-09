package com.example.lab8q1add;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExpenseActivity extends AppCompatActivity {

    EditText etAmount, etCategory;
    Button btnAddExpense;
    TextView tvSummary;
    DBHelper db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        tvSummary = findViewById(R.id.tvSummary);
        db = new DBHelper(this);

        btnAddExpense.setOnClickListener(v -> {
            String category = etCategory.getText().toString();
            String amount = etAmount.getText().toString();
            String date = sdf.format(new Date());
            db.addExpense(category, amount, date);
            Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();
            showSummary();
        });

        showSummary();
    }

    void showSummary() {
        Cursor c = db.getExpenses();
        int total = 0;
        while (c.moveToNext()) {
            total += Integer.parseInt(c.getString(2)); // amount
        }
        Cursor budgetCursor = db.getBudget();
        String budget = "N/A", limit = "N/A";
        if (budgetCursor.moveToFirst()) {
            budget = budgetCursor.getString(0);
            limit = budgetCursor.getString(1);
        }

        tvSummary.setText("Total Budget: " + budget + "\nLimit: " + limit + "\nExpenses: " + total);
    }
}
