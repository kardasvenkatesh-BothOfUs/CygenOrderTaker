package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayEndReportResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("company_name")
    @Expose
    public String company_name;

    @SerializedName("Order_AVG ")
    @Expose
    public String Order_AVG ;

    @SerializedName("Customer_AVG")
    @Expose
    public String Customer_AVG;

    @SerializedName("total_sale")
    @Expose
    public String total_sale;

    @SerializedName("number_items")
    @Expose
    public String number_items;

    @SerializedName("total_disc")
    @Expose
    public String total_disc;

    @SerializedName("total_other_charge")
    @Expose
    public String total_other_charge;

    @SerializedName("avg_sale")
    @Expose
    public String avg_sale;

    @SerializedName("hourly_report")
    @Expose
    public List<HourlyReportResponse> hourlyReportResults;
    public static class HourlyReportResponse {

        @SerializedName("Hour")
        @Expose
        public String Hour;

        @SerializedName("total_discount")
        @Expose
        public String total_discount;

        @SerializedName("other_charges_amt")
        @Expose
        public String other_charges_amt;

        @SerializedName("total_sale")
        @Expose
        public String total_sale;

        @SerializedName("avg_sale")
        @Expose
        public String avg_sale;

        @SerializedName("total_customer")
        @Expose
        public String total_customer;

        @SerializedName("total_order")
        @Expose
        public String total_order;

        public String getHour() {
            return Hour;
        }

        public void setHour(String hour) {
            Hour = hour;
        }

        public String getTotal_discount() {
            return total_discount;
        }

        public void setTotal_discount(String total_discount) {
            this.total_discount = total_discount;
        }

        public String getOther_charges_amt() {
            return other_charges_amt;
        }

        public void setOther_charges_amt(String other_charges_amt) {
            this.other_charges_amt = other_charges_amt;
        }

        public String getTotal_sale() {
            return total_sale;
        }

        public void setTotal_sale(String total_sale) {
            this.total_sale = total_sale;
        }

        public String getAvg_sale() {
            return avg_sale;
        }

        public void setAvg_sale(String avg_sale) {
            this.avg_sale = avg_sale;
        }

        public String getTotal_customer() {
            return total_customer;
        }

        public void setTotal_customer(String total_customer) {
            this.total_customer = total_customer;
        }

        public String getTotal_order() {
            return total_order;
        }

        public void setTotal_order(String total_order) {
            this.total_order = total_order;
        }
    }

    @SerializedName("category_report")
    @Expose
    public List<CategoryReportResponse> categoryReportResults;
    public static class CategoryReportResponse {

        @SerializedName("category")
        @Expose
        public String category;

        @SerializedName("total_qty")
        @Expose
        public String total_qty;

        @SerializedName("total_cost")
        @Expose
        public String total_cost;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTotal_qty() {
            return total_qty;
        }

        public void setTotal_qty(String total_qty) {
            this.total_qty = total_qty;
        }

        public String getTotal_cost() {
            return total_cost;
        }

        public void setTotal_cost(String total_cost) {
            this.total_cost = total_cost;
        }
    }

    @SerializedName("payment_report")
    @Expose
    public List<PaymentReportResponse> paymentReportResults;
    public static class PaymentReportResponse {

        @SerializedName("payment_type")
        @Expose
        public String payment_type;

        @SerializedName("payment")
        @Expose
        public String payment;

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
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
