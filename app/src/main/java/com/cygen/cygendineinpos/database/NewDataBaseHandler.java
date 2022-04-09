package com.cygen.cygendineinpos.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewDataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //datdbase name
    private static final String DATABASE_NAME = "POSNewwDataBase";
    //table anme
    private static final String BILL_ITEMS = "bill_items";
    private static final String BILL_ADDONS = "productAddOnsTable";
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT_ID = "productId";
    private static final String KEY_SEQUENCE_ID = "sequenceId";
    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_TEMP_ID = "tempId";
    private static final String KEY_QTY = "productQty";
    private static final String KEY_SELLING_PRICE = "productPrice";
    private static final String KEY_ADD_ON_NAME = "productAddOnName";
    private static final String KEY_ADD_ON_PRICE = "productAddOnPrice";
    private static final String KEY_ADD_ON_ID = "productAddOnId";
    private static final String KEY_ADD_ON_QTY = "productAddOnQty";
    private static final String KEY_TOTAL_PRICE = "productTotalPrice";

    String quantity, position;
    Double total_price = 0.0;
    List<String> sumPriceList, sumQtyList;

    public NewDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + BILL_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEMP_ID + " TEXT," + KEY_SEQUENCE_ID + " TEXT," + KEY_PRODUCT_ID + " TEXT,"
                + KEY_PRODUCT_NAME + " TEXT," + KEY_QTY + " TEXT,"
                + KEY_SELLING_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE1 = "CREATE TABLE " + BILL_ADDONS + "("
                + KEY_ID + " TEXT," + KEY_TEMP_ID + " TEXT," + KEY_ADD_ON_ID + " TEXT," + KEY_ADD_ON_NAME + " TEXT,"
                + KEY_ADD_ON_PRICE + " TEXT," + KEY_ADD_ON_QTY + " TEXT" + ")";
        db.execSQL(CREATE_TABLE1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BILL_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_ADDONS);

        // Create tables again
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMP_ID, product.get_temp_id()); // Contact Name
        values.put(KEY_SEQUENCE_ID, product.get_index()); // Contact Name
        values.put(KEY_PRODUCT_ID, product.get_product_id()); // Contact Name
        values.put(KEY_PRODUCT_NAME, product.get_product_name()); // Contact Name
        values.put(KEY_SELLING_PRICE, product.get_price()); // Contact Name
        values.put(KEY_QTY, product.get_qty()); // Contact Name
        // Inserting Row
        db.insert(BILL_ITEMS, null, values);
        db.close(); // Closing database connection
    }

    public void addAddOns(AddOns addOns) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, addOns.get_product_id()); // Contact Name
        values.put(KEY_ADD_ON_ID, addOns.get_addOnsId()); // Contact Name
        values.put(KEY_ADD_ON_NAME, addOns.get_addOnsName()); // Contact Name
        values.put(KEY_ADD_ON_PRICE, addOns.get_addOnsPrice()); // Contact Name
        values.put(KEY_ADD_ON_QTY, addOns.get_addOnsQty()); // Contact Name
        // Inserting Row
        db.insert(BILL_ADDONS, null, values);
        db.close(); // Closing database connection
    }

    public void deleteCategory(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BILL_ITEMS, KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.get_product_id())});
        db.close();
    }

    public void deleteCategoryWithSNo(String item_id, String sNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, item_id); // Contact Name
        values.put(KEY_ADD_ON_ID, sNo); // Contact Name

        // Inserting Row
        db.delete(BILL_ITEMS, KEY_PRODUCT_ID + "=" + item_id + " AND " + KEY_ADD_ON_ID + "=" + sNo, null);
        db.close(); // Closing database connection

    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CONTACTS,null,null);
        //db.execSQL("delete  from"+ TABLE_CONTACTS);
        db.execSQL("delete from " + BILL_ITEMS);
        db.execSQL("delete from " + BILL_ADDONS);
        db.close();
    }

    public void updateQuantity(String cat_id, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cat_id); // Contact Name
        values.put(KEY_QTY, qty); // Contact Name

        // Inserting Row
        db.update(BILL_ITEMS, values, KEY_PRODUCT_ID + "=" + cat_id, null);
        db.close(); // Closing database connection

    }

    public void updateQuantityWithSno(String cat_id, String sno, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cat_id); // Contact Name
        values.put(KEY_ID, sno); // Contact Name
        values.put(KEY_QTY, qty); // Contact Name

        // Inserting Row
        db.update(BILL_ITEMS, values, KEY_PRODUCT_ID + "=" + cat_id + " AND " + KEY_SEQUENCE_ID + "=" + sno, null);
        db.close(); // Closing database connection

    }

    public void updateQuantityWithAddOn(String cat_id, String addOn_id, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cat_id); // Contact Name
        values.put(KEY_ADD_ON_ID, addOn_id); // Contact Name
        values.put(KEY_ADD_ON_QTY, qty); // Contact Name

        // Inserting Row
        db.update(BILL_ADDONS, values, KEY_ID + "=" + cat_id + " AND " + KEY_ADD_ON_ID + "=" + addOn_id, null);
        db.close(); // Closing database connection

    }

    public void updatePrice(String cat_id, String price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cat_id); // Contact Name
        values.put(KEY_SELLING_PRICE, price); // Contact Name

        // Inserting Row
        db.update(BILL_ITEMS, values, KEY_PRODUCT_ID + "=" + cat_id, null);
        db.close(); // Closing database connection

    }

    public String getQty(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
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

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id + " AND " + KEY_ID + "=" + sno;
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
    public String getAddOns(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("productAddOnName"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }

    public String getAddOnsId(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("productAddOnId"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }

    public String getSNo(String cat_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndex("id"));
        }

        if (quantity == null) {
            quantity = "0";
        }

        return quantity;
    }

    public String getAddOnsQty(String id, String index) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ADDONS + " WHERE " + KEY_ADD_ON_ID + " = " + id + " AND " + KEY_ID + "=" + index;
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

        String query = "SELECT * FROM " + BILL_ITEMS + " WHERE " + KEY_PRODUCT_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return "No";
        }

        cursor.close();
        return "Yes";

    }

    public String checkAddOnExists(String add_on_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + BILL_ADDONS + " WHERE " + KEY_ADD_ON_ID + " = " + add_on_id;
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

        String query = "SELECT * FROM " + BILL_ITEMS;
        Cursor cursor = db.rawQuery(query, null);
        sumPriceList = new ArrayList<>();
        sumQtyList = new ArrayList<>();
        total_price = 0.0;

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String tprice = cursor.getString(cursor
                        .getColumnIndex("productPrice"));
                String aPrice = cursor.getString(cursor
                        .getColumnIndex("productAddOnPrice"));

                List<String> priceList = Arrays.asList(aPrice.split(","));
//                Log.d("akjsdhkj", )

                Double sum = 0.00;
                for (int i = 0; i < priceList.size(); i++) {
                    sum = sum + Double.parseDouble(priceList.get(i));
                }

                Double subTotal = 0.00;
                subTotal = Double.parseDouble(tprice) + sum;
                String qty = cursor.getString(cursor
                        .getColumnIndex("productQty"));

                sumPriceList.add(String.valueOf(subTotal));
                sumQtyList.add(qty);

                cursor.moveToNext();
            }
        }

        for (int i = 0; i < sumPriceList.size(); i++) {

            String price = sumPriceList.get(i);
            String qty = sumQtyList.get(i);
            total_price = Double.parseDouble(price) * Integer.parseInt(qty) + total_price;
//            Log.d("SUMTOTAL", "" + total_price);
        }

        return total_price;
    }

    public List<Product> getAllProducts() {
        List<Product> contactList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + BILL_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product contact = new Product();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_temp_id(cursor.getString(1));
                contact.set_index(cursor.getString(2));
                contact.set_product_id(cursor.getString(3));
                contact.set_product_name(cursor.getString(4));
                contact.set_qty(cursor.getString(5));
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
        String selectQuery = "SELECT  * FROM " + BILL_ADDONS + " WHERE " + KEY_ID + " = " + cat_id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddOns contact = new AddOns();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_product_id(cursor.getString(1));
                contact.set_addOnsId(cursor.getString(2));
                contact.set_addOnsName(cursor.getString(3));
                contact.set_addOnsPrice(cursor.getString(4));
                contact.set_addOnsQty(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

}
