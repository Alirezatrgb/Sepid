package com.example.TBook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.TBook.entity.Products;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "database";

    public static final String DATABASE_NAME = "ProductsDB";
    public static final int VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);

        Log.e(TAG, "constructor : DbHelper");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String query = "create table Products(id integer primary key , name varchar(40) , Image text , price integer , writer varchar(25) , information text)";

        sqLiteDatabase.execSQL(query);

        Log.e(TAG, "onCreate : create table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {
            ArrayList<Products>arrayList;
            String query = "Alter table student add column age integer";
            sqLiteDatabase.execSQL(query);
        } catch (Exception ex) {
        }

    }

    public long insert(Products products) {
//        String query = "insert into student (name,marid) values('" + student.getName() + "'," + student.getMarid() + ")";
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL(query);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", products.getName());
        contentValues.put("Image", products.getImage());
        contentValues.put("writer", products.getWriter());
        contentValues.put("information", products.getInformation());
        contentValues.put("price", products.getPrice());



        long result = sqLiteDatabase.insert("products", null, contentValues);
        sqLiteDatabase.close();
        return result;

    }





   /* public ArrayList<Products> select() {
        ArrayList<Products> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("product", new String[]{"name", "information", "writer" , "Image"}, new long "price");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long price = cursor.getLong(cursor.getColumnIndex("price"));
                String writer = cursor.getString(cursor.getColumnIndex("writer"));
                String information = cursor.getString(cursor.getColumnIndex("information"));
                String Image = cursor.getString(cursor.getColumnIndex("Image"));

                Products products = new Products(id, name, Image, price, writer, information);
                list.add(products);
            }
        }


        cursor.close();
        sqLiteDatabase.close();
        return list;
    }*/

  /*  public ArrayList<Products> selectByName(String pName) {
        ArrayList<Products> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String str = "%" + pName + "%";
        Cursor cursor = sqLiteDatabase.query("product", new String[]{"id", "name", "marid"}, "name like ?", new String[]{str}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long price = cursor.getLong(cursor.getColumnIndex("price"));
                String writer = cursor.getString(cursor.getColumnIndex("writer"));
                String information = cursor.getString(cursor.getColumnIndex("information"));
                String Image = cursor.getString(cursor.getColumnIndex("Image"));


                Products student = new Products(id, name, Image, price, writer, information);
                list.add(student);
            }
        }

        cursor.close();
        sqLiteDatabase.close();
        return list;
    }*/


}
