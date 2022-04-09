package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrintKotResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("customer")
    @Expose
    public String customer;

    @SerializedName("kot")
    @Expose
    public String kot;

    @SerializedName("table")
    @Expose
    public String table;

    @SerializedName("time")
    @Expose
    public String time;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getKot() {
        return kot;
    }

    public void setKot(String kot) {
        this.kot = kot;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("item_list")
    @Expose
    public List<ProductListResponse> productResults;

    public List<ProductListResponse> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductListResponse> productResults) {
        this.productResults = productResults;
    }

    public static class ProductListResponse {

        @SerializedName("item_id")
        @Expose
        public String item_id;

        @SerializedName("item_name")
        @Expose
        public String item_name;

        @SerializedName("sales_qty")
        @Expose
        public String sales_qty;

        @SerializedName("unit_total_cost")
        @Expose
        public String unit_total_cost;

        @SerializedName("price_per_unit")
        @Expose
        public String price_per_unit;

        @SerializedName("tax")
        @Expose
        public String tax;

        @SerializedName("total_cost")
        @Expose
        public String total_cost;

        @SerializedName("tax_amt")
        @Expose
        public String tax_amt;


        @SerializedName("addonlist")
        @Expose
        public List<ProductAddOnsListResponse> productAddOnsResults;

        public List<ProductAddOnsListResponse> getProductAddOnsGroupResults() {
            return productAddOnsResults;
        }

        public void setProductAddOnsGroupResults(List<ProductAddOnsListResponse> productAddOnsGroupResults) {
            this.productAddOnsResults = productAddOnsGroupResults;
        }

        public class ProductAddOnsListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("sales_id")
            @Expose
            public String sales_id;

            @SerializedName("item_id")
            @Expose
            public String item_id;

            @SerializedName("addon_id")
            @Expose
            public String addon_id;

            @SerializedName("price")
            @Expose
            public String price;

            @SerializedName("qty")
            @Expose
            public String qty;

            @SerializedName("total_price")
            @Expose
            public String total_price;

            @SerializedName("note")
            @Expose
            public String note;

            @SerializedName("customer_id")
            @Expose
            public String customer_id;

            @SerializedName("addon_name")
            @Expose
            public String addon_name;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSales_id() {
                return sales_id;
            }

            public void setSales_id(String sales_id) {
                this.sales_id = sales_id;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getAddon_id() {
                return addon_id;
            }

            public void setAddon_id(String addon_id) {
                this.addon_id = addon_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getAddon_name() {
                return addon_name;
            }

            public void setAddon_name(String addon_name) {
                this.addon_name = addon_name;
            }

        }

    }

}
