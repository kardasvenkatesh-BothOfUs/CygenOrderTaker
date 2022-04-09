package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RefundReasonsResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("refund_list")
    @Expose
    public List<RefundListResponse> productResults;

    public List<RefundListResponse> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<RefundListResponse> productResults) {
        this.productResults = productResults;
    }


    public static class RefundListResponse  {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("refund_type")
        @Expose
        public String refund_type;

        @SerializedName("status")
        @Expose
        public String status;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRefund_type() {
            return refund_type;
        }

        public void setRefund_type(String refund_type) {
            this.refund_type = refund_type;
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
