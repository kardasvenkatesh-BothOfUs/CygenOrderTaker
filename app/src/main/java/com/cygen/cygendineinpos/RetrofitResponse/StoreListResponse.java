package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreListResponse {

    @SerializedName("success")
    @Expose
    public String success;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("lists")
    @Expose
    public List<StoreDataResponse> storeList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StoreDataResponse> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreDataResponse> storeList) {
        this.storeList = storeList;
    }

    public static class StoreDataResponse {

        @SerializedName("storeId")
        @Expose
        public String storeId;

        @SerializedName("vendor_name")
        @Expose
        public String vendor_name;

        @SerializedName("vendor_company_name")
        @Expose
        public String vendor_company_name;

        @SerializedName("delivery_distance")
        @Expose
        public String delivery_distance;

        @SerializedName("delivery_time")
        @Expose
        public String delivery_time;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("store_address")
        @Expose
        public String store_address;

        @SerializedName("store_lat")
        @Expose
        public String store_lat;

        @SerializedName("store_long")
        @Expose
        public String store_long;

        @SerializedName("distance")
        @Expose
        public String distance;

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVendor_company_name() {
            return vendor_company_name;
        }

        public void setVendor_company_name(String vendor_company_name) {
            this.vendor_company_name = vendor_company_name;
        }

        public String getDelivery_distance() {
            return delivery_distance;
        }

        public void setDelivery_distance(String delivery_distance) {
            this.delivery_distance = delivery_distance;
        }

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getStore_lat() {
            return store_lat;
        }

        public void setStore_lat(String store_lat) {
            this.store_lat = store_lat;
        }

        public String getStore_long() {
            return store_long;
        }

        public void setStore_long(String store_long) {
            this.store_long = store_long;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

    }

}
