package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("outllet_id")
    @Expose
    public String outllet_id;

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

        @SerializedName("cat_id")
        @Expose
        public String cat_id;

        @SerializedName("subcategory_code")
        @Expose
        public String subcategory_code;

        @SerializedName("subcategory_name")
        @Expose
        public String subcategory_name;

        @SerializedName("description")
        @Expose
        public String description;

        @SerializedName("company_id")
        @Expose
        public String company_id;

        @SerializedName("subcat_image")
        @Expose
        public String subcat_image;

        @SerializedName("status")
        @Expose
        public String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getSubcategory_code() {
            return subcategory_code;
        }

        public void setSubcategory_code(String subcategory_code) {
            this.subcategory_code = subcategory_code;
        }

        public String getSubcategory_name() {
            return subcategory_name;
        }

        public void setSubcategory_name(String subcategory_name) {
            this.subcategory_name = subcategory_name;
        }

        public String getSubcat_image() {
            return subcat_image;
        }

        public void setSubcat_image(String subcat_image) {
            this.subcat_image = subcat_image;
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

    public String getOutllet_id() {
        return outllet_id;
    }

    public void setOutllet_id(String outllet_id) {
        this.outllet_id = outllet_id;
    }
}
