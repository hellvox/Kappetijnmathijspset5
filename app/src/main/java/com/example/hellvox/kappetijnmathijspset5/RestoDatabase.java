package com.example.hellvox.kappetijnmathijspset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestoDatabase extends SQLiteOpenHelper {

    // Initialize variables
    private static RestoDatabase instance;

    private RestoDatabase(Context context) {
        super(context, "todo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
          "create table if not exists orders" +
                  "( _id integer primary key, title text, price integer, amount integer, url text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);

    }

    public void insertOrder (int id, String title, int price, int amount, String url) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("title", title);
        contentValues.put("price", price);
        contentValues.put("amount", amount);
        contentValues.put("url", url);
        db.insert("orders", null, contentValues);
    }

    public void update(int id, int amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        db.update("orders",contentValues, "_id = " + id, null);

    }

    public int get(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from orders where _id="+id, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return 0;
        }
        return cursor.getInt(cursor.getColumnIndex( "amount"));
    }

    public int getprice(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from orders where _id="+id, null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return 0;
        }
        return cursor.getInt(cursor.getColumnIndex( "price"));
    }

    public void delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("orders","_id = " + id, null);
    }

    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("orders", null, null);
    }

    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select * from orders order by title", null);
    }

    public static RestoDatabase getInstance(Context context) {
        if (instance != null)
            return instance;
        else {
            instance = new RestoDatabase(context);
            return instance;
        }
    }
}
