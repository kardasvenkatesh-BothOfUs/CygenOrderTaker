package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreUsersListResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("outlet_login_list")
    @Expose
    public List<UsersListResponse> usersResults;

    public List<UsersListResponse> getProductResults() {
        return usersResults;
    }

    public void setProductResults(List<UsersListResponse> productResults) {
        this.usersResults = productResults;
    }

    public static class UsersListResponse {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("username")
        @Expose
        public String username;

        @SerializedName("firstname")
        @Expose
        public String firstname;

        @SerializedName("terminal_type")
        @Expose
        public String terminal_type;

        @SerializedName("outlet_id")
        @Expose
        public String outlet_id;

        @SerializedName("user_id")
        @Expose
        public String user_id;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getTerminal_type() {
            return terminal_type;
        }

        public void setTerminal_type(String terminal_type) {
            this.terminal_type = terminal_type;
        }

        public String getOutlet_id() {
            return outlet_id;
        }

        public void setOutlet_id(String outlet_id) {
            this.outlet_id = outlet_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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
