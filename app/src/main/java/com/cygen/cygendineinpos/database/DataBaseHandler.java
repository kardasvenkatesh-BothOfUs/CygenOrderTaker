package com.cygen.cygendineinpos.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //datdbase name
    private static final String DATABASE_NAME = "RestaurantPOSNewDataBase2";
    //table anme
    private static final String TABLE_NAME = "productsPOSNewTable2";
    private static final String TABLE_NAME_ADDONS = "productsPOSNewAddOnTable2";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_ID = "productId";
    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_QTY = "productQty";
    private static final String KEY_NOTE = "productNote";
    private static final String KEY_SELLING_PRICE = "productPrice";

    private static final String KEY_ADD_ON_NAME = "productAddOnName";
    private static final String KEY_ADD_ON_PRICE = "productAddOnPrice";
    private static final String KEY_ADD_ON_ID = "productAddOnId";
    private static final String KEY_ADD_ON_GROUP_ID = "productAddOnGroupId";
    private static final String KEY_KOT_ID = "productKotId";
    private static final String KEY_ADD_ON_QTY = "productAddOnQty";
    private static final String KEY_ADD_ON_PRODUCT_PRIMARY = "productAddOnPrimary";

    String quantity, position;
    Double total_price = 0.0;
    Double total_products_price = 0.0;
    Double total_addons_price = 0.0;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRODUCT_ID + " TEXT,"
                + KEY_KOT_ID + " TEXT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_QTY + " TEXT,"
                + KEY_NOTE + " TEXT,"
                + KEY_SELLING_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME_ADDONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRODUCT_ID + " TEXT," + KEY_KOT_ID + " TEXT," + KEY_ADD_ON_GROUP_ID + " TEXT," + KEY_ADD_ON_ID + " TEXT," + KEY_ADD_ON_NAME + " TEXT,"
                + KEY_ADD_ON_PRICE + " TEXT,"+ KEY_ADD_ON_QTY + " TEXT," + KEY_ADD_ON_PRODUCT_PRIMARY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ADDONS);

        // Create tables again
        onCreate(db);
    }

    public long addCategory(Product category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, category.get_product_id()); // Contact Name
        values.put(KEY_KOT_ID, category.get_product_id()); // Contact Name
        values.put(KEY_PRODUCT_NAME, category.get_product_name()); // Contact Name
        values.put(KEY_QTY, category.get_qty()); // Contact Name
        values.put(KEY_NOTE, category.get_note()); // Contact Name
        values.put(KEY_SELLING_PRICE, category.get_price()); // Contact Name

        // Inserting Row
        Long rowid =db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return rowid;
    }

    public void addAddOns(AddOns category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, category.get_product_id()); // Contact Name
        values.put(KEY_KOT_ID, category.get_kot_id()); // Contact Name
        values.put(KEY_ADD_ON_GROUP_ID, category.get_addOn_group_id()); // Contact Name
        values.put(KEY_ADD_ON_ID, category.get_addOnsId()); // Contact Name
        values.put(KEY_ADD_ON_NAME, category.get_addOnsName()); // Contact Name
        values.put(KEY_ADD_ON_PRICE, category.get_addOnsPrice()); // Contact Name
        values.put(KEY_ADD_ON_QTY, category.get_addOnsQty()); // Contact Name
        values.put(KEY_ADD_ON_PRODUCT_PRIMARY, category.get_product_primary_id()); // Contact Name

        // Inserting Row
        db.insert(TABLE_NAME_ADDONS, null, values);
        db.close(); // Closing database connection
    }

    public void deleteCategory(Product category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(category.get_id())});
        db.close();
    }


    public void deleteAddOn(String primaryid,String aId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADD_ON_ID, aId); // Contact Name

        // Inserting Row
        db.delete(TABLE_NAME_ADDONS, KEY_ADD_ON_PRODUCT_PRIMARY + "=" + primaryid+" AND "+KEY_ADD_ON_ID+" = "+aId, null);
        db.close(); // Closing database connection

    }



    public void deleteAddOnGroupId(String aId, String pId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, pId); // Contact Name
        values.put(KEY_ADD_ON_GROUP_ID, aId); // Contact Name

        // Inserting Row
        db.delete(TABLE_NAME_ADDONS, KEY_PRODUCT_ID + "=" + pId + " AND " + KEY_ADD_ON_GROUP_ID + "=" + aId, null);
        db.close(); // Closing database connection
    }

    public void deleteAddOnWithProductId(String sNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, sNo); // Contact Name

        // Inserting Row
        db.delete(TABLE_NAME_ADDONS, KEY_ADD_ON_PRODUCT_PRIMARY + "=" + sNo, null);
        db.close(); // Closing database connection

    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CONTACTS,null,null);
        //db.execSQL("delete  from"+ TABLE_CONTACTS);
        db.execSQL("delete from " + TABLE_NAME);
        db.execSQL("delete from " + TABLE_NAME_ADDONS);
        db.close();
    }

    public void updateQuantity(String id,String cat_id, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cat_id); // Contact Name
        values.put(KEY_QTY, qty); // Contact Name

        // Inserting Row
        db.update(TABLE_NAME, values, KEY_ID + "=" + id+" AND "+KEY_PRODUCT_ID+" = "+cat_id, null);
        db.close(); // Closing database connection

    }
    public void updateNote(String cat_id,String rowid, String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cat_id); // Contact Name
        values.put(KEY_NOTE, note); // Contact Name

        // Inserting Row
        db.update(TABLE_NAME, values, KEY_ID+ "=" + rowid, null);
        db.close(); // Closing database connection

    }


    public void updateAddOnQuantity(String addOn_id,String primaryid, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADD_ON_ID, addOn_id); // Contact Name
        values.put(KEY_ADD_ON_QTY, qty); // Contact Name

        // Inserting Row
        db.update(TABLE_NAME_ADDONS, values, KEY_ADD_ON_PRODUCT_PRIMARY + "=" + primaryid+ " AND "+KEY_ADD_ON_ID + "=" + addOn_id, null);
        db.close(); // Closing database connection

    }


    @SuppressLint("Range")
    public String getQty(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("productQty"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }

    @SuppressLint("Range")
    public String getQtyWithSno(String cat_id, String sno) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id + " AND " + KEY_ID + "=" + sno;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("productQty"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }



    @SuppressLint("Range")
    public String getAddOnsQty(String cat_id,String productid) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_ADDONS + " WHERE " + KEY_ADD_ON_PRODUCT_PRIMARY + " = " + cat_id+" AND "+KEY_ADD_ON_ID+"="+productid;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("productAddOnQty"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }

    public String checkProductExists(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return "No";
        }

        cursor.close();
        return "Yes";

    }


    public String checkAddOnExists(String add_on_id,String primaryid) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_ADDONS + " WHERE " + KEY_ADD_ON_PRODUCT_PRIMARY + " = " + primaryid+" AND "+KEY_ADD_ON_ID+" = "+add_on_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return "No";
        }

        cursor.close();
        return "Yes";

    }

    public Double getTotalPrice() {

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db1 = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String query1 = "SELECT * FROM " + TABLE_NAME_ADDONS;
        Cursor cursor1 = db1.rawQuery(query1, null);

        total_products_price = 0.0;
        total_addons_price = 0.0;

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                @SuppressLint("Range") String tprice = cursor.getString(cursor
                        .getColumnIndex("productPrice"));

                Double subTotal = 0.00;
                subTotal = Double.parseDouble(tprice);
                @SuppressLint("Range") String qty = cursor.getString(cursor
                        .getColumnIndex("productQty"));

                Log.d("kjhgjkf", qty);

                total_products_price = subTotal * Integer.parseInt(qty) + total_products_price;

                cursor.moveToNext();
            }
        }

        if (cursor1 != null) {
            if (cursor1.moveToFirst()) {
                while (cursor1.isAfterLast() == false) {

                    @SuppressLint("Range") String aPrice = cursor1.getString(cursor1
                            .getColumnIndex("productAddOnPrice"));

                    Double subTotal = 0.00;
                    subTotal = Double.parseDouble(aPrice);
                    @SuppressLint("Range") String qty = cursor1.getString(cursor1
                            .getColumnIndex("productAddOnQty"));

                    total_addons_price = subTotal * Integer.parseInt(qty) + total_addons_price;

                    cursor1.moveToNext();
                }
            }
        }

        total_price = total_addons_price + total_products_price;

        return total_price;
    }

    public List<Product> getAllCategories() {
        List<Product> contactList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product contact = new Product();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_product_id(cursor.getString(1));
                contact.set_kot(cursor.getString(2));
                contact.set_product_name(cursor.getString(3));
                contact.set_qty(cursor.getString(4));
                contact.set_note(cursor.getString(5));
                contact.set_price(cursor.getString(6));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<AddOns> getAllAddOns(String cat_id) {
        List<AddOns> contactList = new ArrayList<AddOns>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_ADDONS + " WHERE " + KEY_ADD_ON_PRODUCT_PRIMARY + " = " + cat_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddOns contact = new AddOns();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_product_id(cursor.getString(1));
                contact.set_kot_id(cursor.getString(2));
                contact.set_addOn_group_id(cursor.getString(3));
                contact.set_addOnsId(cursor.getString(4));
                contact.set_addOnsName(cursor.getString(5));
                contact.set_addOnsPrice(cursor.getString(6));
                contact.set_addOnsQty(cursor.getString(7));
                contact.set_product_primary_id(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


}
