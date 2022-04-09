package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReservationResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("outlet_id")
    @Expose
    public String outlet_id;

    @SerializedName("coupon_list")
    @Expose
    public List<ReservationsListResponse> reservationListResponses;

    public List<ReservationsListResponse> getProductResults() {
        return reservationListResponses;
    }

    public void setProductResults(List<ReservationsListResponse> productResults) {
        this.reservationListResponses = productResults;
    }

    public static class ReservationsListResponse {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("table_id")
        @Expose
        public String table_id;

        @SerializedName("reserve_date")
        @Expose
        public String reserve_date;

        @SerializedName("reserve_time")
        @Expose
        public String reserve_time;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("mobile")
        @Expose
        public String mobile;

        @SerializedName("number_of_people")
        @Expose
        public String number_of_people;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("added_date")
        @Expose
        public String added_date;

        @SerializedName("message")
        @Expose
        public String message;

        @SerializedName("res_status")
        @Expose
        public String res_status;

        @SerializedName("booking_status")
        @Expose
        public String booking_status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTable_id() {
            return table_id;
        }

        public void setTable_id(String table_id) {
            this.table_id = table_id;
        }

        public String getReserve_date() {
            return reserve_date;
        }

        public void setReserve_date(String reserve_date) {
            this.reserve_date = reserve_date;
        }

        public String getReserve_time() {
            return reserve_time;
        }

        public void setReserve_time(String reserve_time) {
            this.reserve_time = reserve_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNumber_of_people() {
            return number_of_people;
        }

        public void setNumber_of_people(String number_of_people) {
            this.number_of_people = number_of_people;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdded_date() {
            return added_date;
        }

        public void setAdded_date(String added_date) {
            this.added_date = added_date;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRes_status() {
            return res_status;
        }

        public void setRes_status(String res_status) {
            this.res_status = res_status;
        }

        public String getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(String booking_status) {
            this.booking_status = booking_status;
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

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

}
