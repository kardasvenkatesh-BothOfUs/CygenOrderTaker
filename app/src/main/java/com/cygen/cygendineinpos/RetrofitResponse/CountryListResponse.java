package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryListResponse {

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

        @SerializedName("country_code")
        @Expose
        public String country_code;

        @SerializedName("country")
        @Expose
        public String country;

        @SerializedName("added_on")
        @Expose
        public String added_on;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAdded_on() {
            return added_on;
        }

        public void setAdded_on(String added_on) {
            this.added_on = added_on;
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
