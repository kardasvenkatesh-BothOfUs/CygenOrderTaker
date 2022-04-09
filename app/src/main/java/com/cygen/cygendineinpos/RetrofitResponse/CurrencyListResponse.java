package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyListResponse {
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("currency_list")
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

        @SerializedName("currency_name")
        @Expose
        public String currency_name;

        @SerializedName("currency_code")
        @Expose
        public String currency_code;

        @SerializedName("currency")
        @Expose
        public String currency;

        @SerializedName("symbol")
        @Expose
        public String symbol;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("outlet_id")
        @Expose
        public String outlet_id;

        public String getId() {
            return id;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public String getCurrency_code() {
            return currency_code;
        }

        public String getCurrency() {
            return currency;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getStatus() {
            return status;
        }

        public String getOutlet_id() {
            return outlet_id;
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
