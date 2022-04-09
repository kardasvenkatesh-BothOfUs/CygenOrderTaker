package com.cygen.cygendineinpos.database;

public class Product {

    int _id;
    String _temp_id;
    String _index;
    String _product_id;
    String _product_name;
    String _qty;
    String _price;
    String _kot;
    String _addOnsName;
    String _addOnsId;
    String _addOnsArray;
    String _addOnsPrice;
    String _addOnsQty;
    String _total_price;

    String _note;

    // Empty constructor
    public Product() {

    }

    public Product(String productId, String name, String price, String count) {
        this._product_id = productId;
        this._product_name = name;
        this._price = price;
        this._qty = count;
    }

    public Product(String productId, String kot, String name, String count, String price) {
        this._product_id = productId;
        this._kot = kot;
        this._product_name = name;
        this._qty = count;
        this._price = price;
    }

    public Product(String productId, String kot, String name, String count, String note, String price) {
        this._product_id = productId;
        this._kot = kot;
        this._product_name = name;
        this._qty = count;
        this._note = note;
        this._price = price;
    }

    public Product(String catid, String qty) {
        this._product_id = catid;
        this._qty = qty;
    }

    public String get_kot() {
        return _kot;
    }

    public void set_kot(String _kot) {
        this._kot = _kot;
    }


    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }


    public Product(int _id) {
        this._id = _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_product_id() {
        return _product_id;
    }

    public void set_product_id(String _product_id) {
        this._product_id = _product_id;
    }

    public String get_product_name() {
        return _product_name;
    }

    public void set_product_name(String _product_name) {
        this._product_name = _product_name;
    }

    public String get_qty() {
        return _qty;
    }

    public void set_qty(String _qty) {
        this._qty = _qty;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public int get_id() {
        return _id;
    }

    public String get_addOnsName() {
        return _addOnsName;
    }

    public void set_addOnsName(String _addOnsName) {
        this._addOnsName = _addOnsName;
    }

    public String get_addOnsArray() {
        return _addOnsArray;
    }

    public void set_addOnsArray(String _addOnsArray) {
        this._addOnsArray = _addOnsArray;
    }

    public String get_addOnsId() {
        return _addOnsId;
    }

    public void set_addOnsId(String _addOnsId) {
        this._addOnsId = _addOnsId;
    }

    public String get_addOnsPrice() {
        return _addOnsPrice;
    }

    public void set_addOnsPrice(String _addOnsPrice) {
        this._addOnsPrice = _addOnsPrice;
    }

    public String get_addOnsQty() {
        return _addOnsQty;
    }

    public void set_addOnsQty(String _addOnsQty) {
        this._addOnsQty = _addOnsQty;
    }

    public String get_total_price() {
        return _total_price;
    }

    public void set_total_price(String _total_price) {
        this._total_price = _total_price;
    }

    public String get_temp_id() {
        return _temp_id;
    }

    public void set_temp_id(String _temp_id) {
        this._temp_id = _temp_id;
    }

    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }
}
