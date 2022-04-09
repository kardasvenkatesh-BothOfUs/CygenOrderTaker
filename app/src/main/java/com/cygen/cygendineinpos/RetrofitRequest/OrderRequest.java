package com.cygen.cygendineinpos.RetrofitRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrderRequest {

    String address;
    String customer_id;
    String cashier_id;
    String grand_total;
    String payment_type;
    String change_return;
    String paid_amt;
    String note;
    String order_type;
    String order_number;
    String order_id;
    String price_id;
    String requested_for;
    String subtotal;
    String tax_type;
    String decice_type;
    String order_device;
    String table_number;
    String outlet_id;
    String tips;
    JSONArray item_list;
    String customer_name;
    String mobile;
    String phone;
    String email;
    String postcode;
    String product_notes;
    String tot_discount_to_all_amt;

    JSONObject productsObject;
    JSONObject addOnsObject;

    public String getTot_discount_to_all_amt() {
        return tot_discount_to_all_amt;
    }

    public void setTot_discount_to_all_amt(String tot_discount_to_all_amt) {
        this.tot_discount_to_all_amt = tot_discount_to_all_amt;
    }


    public JSONArray getItem_list() {
        return item_list;
    }

    public void setItem_list(JSONArray item_list) {
        this.item_list = item_list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getRequested_for() {
        return requested_for;
    }

    public void setRequested_for(String requested_for) {
        this.requested_for = requested_for;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTax_type() {
        return tax_type;
    }

    public void setTax_type(String tax_type) {
        this.tax_type = tax_type;
    }

    public String getDecice_type() {
        return decice_type;
    }

    public void setDecice_type(String decice_type) {
        this.decice_type = decice_type;
    }

    public String getOrder_device() {
        return order_device;
    }

    public void setOrder_device(String order_device) {
        this.order_device = order_device;
    }

    public String getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(String cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getChange_return() {
        return change_return;
    }

    public void setChange_return(String change_return) {
        this.change_return = change_return;
    }

    public String getPaid_amt() {
        return paid_amt;
    }

    public void setPaid_amt(String paid_amt) {
        this.paid_amt = paid_amt;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_id) {
        this.table_number = table_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProduct_notes() {
        return product_notes;
    }

    public void setProduct_notes(String product_notes) {
        this.product_notes = product_notes;
    }


}
