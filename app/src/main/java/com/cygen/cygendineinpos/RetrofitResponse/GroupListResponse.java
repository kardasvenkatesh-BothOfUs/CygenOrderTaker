package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupListResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("group_list")
    @Expose
    public List<AreaListResponse> areaResults;

    public List<AreaListResponse> getProductResults() {
        return areaResults;
    }

    public void setProductResults(List<AreaListResponse> productResults) {
        this.areaResults = productResults;
    }

    public static class AreaListResponse {

        @SerializedName("id")
        @Expose
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
