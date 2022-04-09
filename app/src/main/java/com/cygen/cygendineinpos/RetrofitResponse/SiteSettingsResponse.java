package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SiteSettingsResponse {

    @SerializedName("success")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("site_detail")
    @Expose
    public UserListResponse productResults;

    public static class UserListResponse  {

        @SerializedName("id")
        @Expose
        public String id;



        @SerializedName("site_name")
        @Expose
        public String site_name;

        @SerializedName("site_url")
        @Expose
        public String site_url;

        @SerializedName("copyright")
        @Expose
        public String copyright;

        @SerializedName("domain")
        @Expose
        public String domain;

        @SerializedName("logo")
        @Expose
        public String logo;

        @SerializedName("currency")
        @Expose
        public String currency;

        @SerializedName("currency_id")
        @Expose
        public String currency_id;

        public String getCurrency_id() {
            return currency_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSite_name() {
            return site_name;
        }

        public String getSite_url() {
            return site_url;
        }

        public String getCopyright() {
            return copyright;
        }

        public String getDomain() {
            return domain;
        }

        public String getLogo() {
            return logo;
        }

        public String getCurrency() {
            return currency;
        }


    }

    @SerializedName("company_detail")
    @Expose
    public UserListResponse1 productResults1;

    public static class UserListResponse1  {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("company_code")
        @Expose
        public String company_code;

        @SerializedName("company_name")
        @Expose
        public String company_name;

        @SerializedName("company_website")
        @Expose
        public String company_website;

        @SerializedName("mobile")
        @Expose
        public String mobile;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("company_logo")
        @Expose
        public String company_logo;

        @SerializedName("country")
        @Expose
        public String country;

        @SerializedName("state")
        @Expose
        public String state;

        @SerializedName("city")
        @Expose
        public String city;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("postcode")
        @Expose
        public String postcode;

        @SerializedName("gst_no")
        @Expose
        public String gst_no;

        @SerializedName("vat_no")
        @Expose
        public String vat_no;

        @SerializedName("pan_no")
        @Expose
        public String pan_no;

        @SerializedName("bank_details")
        @Expose
        public String bank_details;

        @SerializedName("category_init")
        @Expose
        public String category_init;

        @SerializedName("outlet_id")
        @Expose
        public String outlet_id;

        @SerializedName("country_id")
        @Expose
        public String country_id;

        @SerializedName("state_id")
        @Expose
        public String state_id;

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getId() {
            return id;
        }

        public String getCompany_code() {
            return company_code;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getCompany_website() {
            return company_website;
        }

        public String getMobile() {
            return mobile;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getCompany_logo() {
            return company_logo;
        }

        public String getCountry() {
            return country;
        }

        public String getState() {
            return state;
        }

        public String getCity() {
            return city;
        }

        public String getAddress() {
            return address;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getGst_no() {
            return gst_no;
        }

        public String getVat_no() {
            return vat_no;
        }

        public String getPan_no() {
            return pan_no;
        }

        public String getBank_details() {
            return bank_details;
        }

        public String getCategory_init() {
            return category_init;
        }

        public String getOutlet_id() {
            return outlet_id;
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
