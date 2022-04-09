package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastInvoiceResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("last_invoice ")
    @Expose
    public String last_invoice;

    @SerializedName("last_order_id")
    @Expose
    public String last_order_id;

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

    public String getLast_invoice() {
        return last_invoice;
    }

    public void setLast_invoice(String last_invoice) {
        this.last_invoice = last_invoice;
    }

    public String getLast_order_id() {
        return last_order_id;
    }

    public void setLast_order_id(String last_order_id) {
        this.last_order_id = last_order_id;
    }

}
