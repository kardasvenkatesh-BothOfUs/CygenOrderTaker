package com.cygen.cygendineinpos.database;

public class AddOns {


    int _id;
    String _product_id;
    String _kot_id;
    String _addOn_group_id;
    String _addOnsName;
    String _addOnsId;
    String _addOnsPrice;
    String _addOnsQty;

    public String get_product_primary_id() {
        return _product_primary_id;
    }

    public void set_product_primary_id(String _product_primary_id) {
        this._product_primary_id = _product_primary_id;
    }

    String _product_primary_id;

    // Empty constructor
    public AddOns() {

    }

    public AddOns(String id, String kotId, String groupId,String addOnId,String addOnName, String addOnPrice, String addOnQty,String _product_primary_id) {
        this._product_id = id;
        this._kot_id = kotId;
        this._addOn_group_id = groupId;
        this._addOnsId = addOnId;
        this._addOnsName = addOnName;
        this._addOnsPrice = addOnPrice;
        this._addOnsQty = addOnQty;
        this._product_primary_id=_product_primary_id;
    }

    public AddOns(String id, String kotId, String groupId,String addOnId,String addOnName, String addOnPrice, String addOnQty) {
        this._product_id = id;
        this._kot_id = kotId;
        this._addOn_group_id = groupId;
        this._addOnsId = addOnId;
        this._addOnsName = addOnName;
        this._addOnsPrice = addOnPrice;
        this._addOnsQty = addOnQty;
    }

    public AddOns(String id, String addOnId,String addOnName, String addOnPrice, String addOnQty) {
        this._product_id = id;
        this._addOnsId = addOnId;
        this._addOnsName = addOnName;
        this._addOnsPrice = addOnPrice;
        this._addOnsQty = addOnQty;
    }

    public AddOns(String catid, String sellingPrice, String qty) {
        this._addOnsId = catid;
        this._addOnsPrice = sellingPrice;
        this._addOnsQty = qty;
    }

    public AddOns(String catid, String sellingPrice) {
        this._addOnsId = catid;
        this._addOnsPrice = sellingPrice;
    }

    public String get_kot_id() {
        return _kot_id;
    }

    public void set_kot_id(String _kot_id) {
        this._kot_id = _kot_id;
    }

    public String get_addOn_group_id() {
        return _addOn_group_id;
    }

    public void set_addOn_group_id(String _addOn_group_id) {
        this._addOn_group_id = _addOn_group_id;
    }

    public AddOns(String productId) {
        this._addOnsId = productId;
    }

    public String get_addOnsName() {
        return _addOnsName;
    }

    public void set_addOnsName(String _addOnsName) {
        this._addOnsName = _addOnsName;
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

    public int get_id() {
        return _id;
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
}
