package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunningOrdersResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

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


    @SerializedName("list")
    @Expose
    public List<ProductListResponse> productResults;

    public List<ProductListResponse> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductListResponse> productResults) {
        this.productResults = productResults;
    }

    public static class ProductListResponse {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("order_type")
        @Expose
        public String order_type;

        @SerializedName("order_number")
        @Expose
        public String order_number;

        @SerializedName("grand_total")
        @Expose
        public String grand_total;

        @SerializedName("table_number")
        @Expose
        public String table_number;

        @SerializedName("del_customer_name")
        @Expose
        public String del_customer_name;

        @SerializedName("del_customer_mobile")
        @Expose
        public String del_customer_mobile;

        @SerializedName("payment_status")
        @Expose
        public String payment_status;

        @SerializedName("time")
        @Expose
        public String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDel_customer_name() {
            return del_customer_name;
        }

        public void setDel_customer_name(String del_customer_name) {
            this.del_customer_name = del_customer_name;
        }

        public String getDel_customer_mobile() {
            return del_customer_mobile;
        }

        public void setDel_customer_mobile(String del_customer_mobile) {
            this.del_customer_mobile = del_customer_mobile;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }




        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getGrand_total() {
            return grand_total;
        }

        public void setGrand_total(String grand_total) {
            this.grand_total = grand_total;
        }

        public String getTable_number() {
            return table_number;
        }

        public void setTable_number(String table_number) {
            this.table_number = table_number;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

    }

}
