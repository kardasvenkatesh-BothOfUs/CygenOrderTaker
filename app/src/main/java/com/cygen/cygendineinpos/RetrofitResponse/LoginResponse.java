package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

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
    public UserListResponse  userResults;

    public static class UserListResponse  {

        @SerializedName("inv_username")
        @Expose
        public String inv_username;

        @SerializedName("inv_userid")
        @Expose
        public String inv_userid;

        @SerializedName("logged_in")
        @Expose
        public String logged_in;

        @SerializedName("role_id")
        @Expose
        public String role_id;

        @SerializedName("role_name")
        @Expose
        public String role_name;

        public String getInv_username() {
            return inv_username;
        }

        public void setInv_username(String inv_username) {
            this.inv_username = inv_username;
        }

        public String getInv_userid() {
            return inv_userid;
        }

        public void setInv_userid(String inv_userid) {
            this.inv_userid = inv_userid;
        }

        public String getLogged_in() {
            return logged_in;
        }

        public void setLogged_in(String logged_in) {
            this.logged_in = logged_in;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
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
