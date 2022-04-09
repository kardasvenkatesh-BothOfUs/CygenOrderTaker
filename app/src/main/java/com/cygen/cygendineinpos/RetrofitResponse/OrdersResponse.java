package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("order_list")
    @Expose
    public List<OrdesListResponse> ordesListResponses;

    public List<OrdesListResponse> getProductResults() {
        return ordesListResponses;
    }

    public void setProductResults(List<OrdesListResponse> productResults) {
        this.ordesListResponses = productResults;
    }

    public static class OrdesListResponse {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("sales_code")
        @Expose
        public String sales_code;

        @SerializedName("sales_date")
        @Expose
        public String sales_date;

        @SerializedName("created_time")
        @Expose
        public String created_time;

        @SerializedName("payment_status")
        @Expose
        public String payment_status;

        @SerializedName("customer_id")
        @Expose
        public String customer_id;

        @SerializedName("customer_name")
        @Expose
        public String customer_name;

        @SerializedName("order_type")
        @Expose
        public String order_type;

        @SerializedName("grand_total")
        @Expose
        public String grand_total;

        @SerializedName("tot_discount_to_all_amt")
        @Expose
        public String tot_discount_to_all_amt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSales_code() {
            return sales_code;
        }

        public void setSales_code(String sales_code) {
            this.sales_code = sales_code;
        }

        public String getSales_date() {
            return sales_date;
        }

        public void setSales_date(String sales_date) {
            this.sales_date = sales_date;
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

        public String getTot_discount_to_all_amt() {
            return tot_discount_to_all_amt;
        }

        public void setTot_discount_to_all_amt(String tot_discount_to_all_amt) {
            this.tot_discount_to_all_amt = tot_discount_to_all_amt;
        }
    }

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

}
