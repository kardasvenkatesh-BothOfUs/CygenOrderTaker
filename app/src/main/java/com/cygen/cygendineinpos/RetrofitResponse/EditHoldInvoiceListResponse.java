package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditHoldInvoiceListResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

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

        @SerializedName("invoice_id")
        @Expose
        public String invoice_id;

        @SerializedName("invoice_date")
        @Expose
        public String invoice_date;

        @SerializedName("reference_id")
        @Expose
        public String reference_id;

        @SerializedName("item_id")
        @Expose
        public String item_id;

        @SerializedName("item_qty")
        @Expose
        public String item_qty;

        @SerializedName("item_price")
        @Expose
        public String item_price;

        @SerializedName("tax")
        @Expose
        public String tax;

        @SerializedName("created_date")
        @Expose
        public String created_date;

        @SerializedName("created_time")
        @Expose
        public String created_time;

        @SerializedName("created_by")
        @Expose
        public String created_by;

        @SerializedName("system_ip")
        @Expose
        public String system_ip;

        @SerializedName("system_name")
        @Expose
        public String system_name;

        @SerializedName("pos")
        @Expose
        public String pos;

        @SerializedName("status")
        @Expose
        public String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getInvoice_date() {
            return invoice_date;
        }

        public void setInvoice_date(String invoice_date) {
            this.invoice_date = invoice_date;
        }

        public String getReference_id() {
            return reference_id;
        }

        public void setReference_id(String reference_id) {
            this.reference_id = reference_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_qty() {
            return item_qty;
        }

        public void setItem_qty(String item_qty) {
            this.item_qty = item_qty;
        }

        public String getItem_price() {
            return item_price;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getCreated_by() {
            return created_by;
        }

        public void setCreated_by(String created_by) {
            this.created_by = created_by;
        }

        public String getSystem_ip() {
            return system_ip;
        }

        public void setSystem_ip(String system_ip) {
            this.system_ip = system_ip;
        }

        public String getSystem_name() {
            return system_name;
        }

        public void setSystem_name(String system_name) {
            this.system_name = system_name;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
