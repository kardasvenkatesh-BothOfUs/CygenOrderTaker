package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableLayoutResponse {

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

        @SerializedName("area")
        @Expose
        public String area;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("add_date")
        @Expose
        public String add_date;

        @SerializedName("table_list")
        @Expose
        public List<TablesListResponse> tableLayoutResults;

        public static class TablesListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("table_name")
            @Expose
            public String table_name;

            @SerializedName("table_details")
            @Expose
            public String table_details;

            @SerializedName("table_capacity")
            @Expose
            public String table_capacity;

            @SerializedName("status")
            @Expose
            public String status;

            @SerializedName("area")
            @Expose
            public String area;

            @SerializedName("chair")
            @Expose
            public String chair;

            @SerializedName("table_booked")
            @Expose
            public String table_booked;

            @SerializedName("table_booked_text")
            @Expose
            public String table_booked_text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTable_name() {
                return table_name;
            }

            public void setTable_name(String table_name) {
                this.table_name = table_name;
            }

            public String getTable_details() {
                return table_details;
            }

            public void setTable_details(String table_details) {
                this.table_details = table_details;
            }

            public String getTable_capacity() {
                return table_capacity;
            }

            public void setTable_capacity(String table_capacity) {
                this.table_capacity = table_capacity;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getChair() {
                return chair;
            }

            public void setChair(String chair) {
                this.chair = chair;
            }

            public String getTable_booked() {
                return table_booked;
            }

            public void setTable_booked(String table_booked) {
                this.table_booked = table_booked;
            }

            public String getTable_booked_text() {
                return table_booked_text;
            }

            public void setTable_booked_text(String table_booked_text) {
                this.table_booked_text = table_booked_text;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
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
