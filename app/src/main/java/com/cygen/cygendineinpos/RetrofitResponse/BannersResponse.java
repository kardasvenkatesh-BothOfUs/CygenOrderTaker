package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannersResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("banner_list")
    @Expose
    public List<UserListResponse> bannerResults;

    public static class UserListResponse  {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("banner_image")
        @Expose
        public String banner_image;

        @SerializedName("description_banner")
        @Expose
        public String description_banner;

        @SerializedName("title_banner")
        @Expose
        public String title_banner;

        @SerializedName("banner_type")
        @Expose
        public String banner_type;

        @SerializedName("status")
        @Expose
        public String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner_image() {
            return banner_image;
        }

        public void setBanner_image(String banner_image) {
            this.banner_image = banner_image;
        }

        public String getDescription_banner() {
            return description_banner;
        }

        public void setDescription_banner(String description_banner) {
            this.description_banner = description_banner;
        }

        public String getTitle_banner() {
            return title_banner;
        }

        public void setTitle_banner(String title_banner) {
            this.title_banner = title_banner;
        }

        public String getBanner_type() {
            return banner_type;
        }

        public void setBanner_type(String banner_type) {
            this.banner_type = banner_type;
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
