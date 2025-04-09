package com.example.lab8q1add;

import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "BudgetDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE budget(total TEXT, limit_amt TEXT)");
        db.execSQL("CREATE TABLE expenses(id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, amount TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS budget");
        db.execSQL("DROP TABLE IF EXISTS expenses");
        onCreate(db);
    }

    public void setBudget(String total, String limit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("budget", null, null);
        ContentValues cv = new ContentValues();
        cv.put("total", total);
        cv.put("limit_amt", limit);
        db.insert("budget", null, cv);
    }

    public Cursor getBudget() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM budget", null);
    }

    public void addExpense(String category, String amount, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("amount", amount);
        cv.put("date", date);
        db.insert("expenses", null, cv);
    }

    public Cursor getExpenses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM expenses", null);
    }
}
