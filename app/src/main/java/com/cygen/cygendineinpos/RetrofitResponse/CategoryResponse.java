package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

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

        @SerializedName("color_code")
        @Expose
        public String color_code;

        @SerializedName("category_code")
        @Expose
        public String category_code;

        @SerializedName("category_name")
        @Expose
        public String category_name;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("company_id")
        @Expose
        public String company_id;

        @SerializedName("cat_image")
        @Expose
        public String cat_image;

        @SerializedName("status")
        @Expose
        public String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_code() {
            return category_code;
        }

        public void setCategory_code(String category_code) {
            this.category_code = category_code;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCat_image() {
            return cat_image;
        }

        public void setCat_image(String cat_image) {
            this.cat_image = cat_image;
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
