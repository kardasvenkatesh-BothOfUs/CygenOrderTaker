package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrintBillResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("del_customer_name")
    @Expose
    public String del_customer_name;

    @SerializedName("del_customer_email")
    @Expose
    public String del_customer_email;

    @SerializedName("del_customer_mobile")
    @Expose
    public String del_customer_mobile;

    @SerializedName("del_customer_address")
    @Expose
    public String del_customer_address;

    @SerializedName("company_name")
    @Expose
    public String company_name;

    @SerializedName("company_address")
    @Expose
    public String company_address;

    @SerializedName("customer")
    @Expose
    public String customer;

    @SerializedName("customer_mobile")
    @Expose
    public String customer_mobile;

    @SerializedName("postcode")
    @Expose
    public String postcode;

    @SerializedName("city")
    @Expose
    public String city;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("company_website")
    @Expose
    public String company_website;

    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("mobile")
    @Expose
    public String mobile;

    @SerializedName("staff")
    @Expose
    public String staff;

    @SerializedName("device")
    @Expose
    public String device;

    @SerializedName("delivery_time")
    @Expose
    public String delivery_time;

    @SerializedName("balance_amount")
    @Expose
    public String balance_amount;

    @SerializedName("delivery_slot")
    @Expose
    public String delivery_slot;

    @SerializedName("sales_date")
    @Expose
    public String sales_date;

    @SerializedName("account_number")
    @Expose
    public String account_number;

    @SerializedName("reg_number")
    @Expose
    public String reg_number;

    @SerializedName("change_return")
    @Expose
    public String change_return;

    @SerializedName("total_amount")
    @Expose
    public String total_amount;

    @SerializedName("price_id")
    @Expose
    public String price_id;

    @SerializedName("grand_total")
    @Expose
    public String grand_total;

    @SerializedName("tips_amount")
    @Expose
    public String tips_amount;

    @SerializedName("paid_amount")
    @Expose
    public String paid_amount;

    @SerializedName("order_type")
    @Expose
    public String order_type;

    @SerializedName("time")
    @Expose
    public String time;

    @SerializedName("tax_rate")
    @Expose
    public String tax_rate;

    @SerializedName("tax_percentage")
    @Expose
    public String tax_percentage;

    @SerializedName("total_discount")
    @Expose
    public String total_discount;

    @SerializedName("tax_vale")
    @Expose
    public String tax_vale;

    @SerializedName("payment_status")
    @Expose
    public String payment_status;

    @SerializedName("abn")
    @Expose
    public String abn;

    @SerializedName("payment_type")
    @Expose
    public String payment_type;

    @SerializedName("qrcode")
    @Expose
    public String qrcode;


    @SerializedName("order_id")
    @Expose
    public String order_id;

    @SerializedName("order_number")
    @Expose
    public String order_number;

    @SerializedName("table_number")
    @Expose
    public String table_number;

    @SerializedName("user_name")
    @Expose
    public String user_name;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    @SerializedName("tax_list_all")
    @Expose
    public UserListResponse userResults;

    public static class UserListResponse  {

        @SerializedName("Tax")
        @Expose
        public String Tax;

        @SerializedName("FREE(0.00)")
        @Expose
        public String free;


        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }


        public String getTax() {
            return Tax;
        }

        public void setTax(String tax) {
            Tax = tax;
        }


    }

    @SerializedName("item_list")
    @Expose
    public List<ProductListResponse> productResults;

    public List<ProductListResponse> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductListResponse> productResults) {
        this.productResults = productResults;
    }

    public static class ProductListResponse {

        @SerializedName("item_note")
        @Expose
        public String item_note;

        @SerializedName("item_id")
        @Expose
        public String item_id;

        @SerializedName("item_name")
        @Expose
        public String item_name;

        @SerializedName("sales_qty")
        @Expose
        public String sales_qty;

        @SerializedName("unit_total_cost")
        @Expose
        public String unit_total_cost;

        @SerializedName("price_per_unit")
        @Expose
        public String price_per_unit;

        @SerializedName("tax")
        @Expose
        public String tax;

        @SerializedName("total_cost")
        @Expose
        public String total_cost;

        @SerializedName("tax_amt")
        @Expose
        public String tax_amt;


        @SerializedName("addonlist")
        @Expose
        public List<ProductAddOnsListResponse> productAddOnsResults;

        public List<ProductAddOnsListResponse> getProductAddOnsGroupResults() {
            return productAddOnsResults;
        }

        public void setProductAddOnsGroupResults(List<ProductAddOnsListResponse> productAddOnsGroupResults) {
            this.productAddOnsResults = productAddOnsGroupResults;
        }

        public class ProductAddOnsListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("sales_id")
            @Expose
            public String sales_id;

            @SerializedName("item_id")
            @Expose
            public String item_id;

            @SerializedName("addon_id")
            @Expose
            public String addon_id;

            @SerializedName("price")
            @Expose
            public String price;

            @SerializedName("qty")
            @Expose
            public String qty;

            @SerializedName("total_price")
            @Expose
            public String total_price;

            @SerializedName("note")
            @Expose
            public String note;

            @SerializedName("customer_id")
            @Expose
            public String customer_id;

            @SerializedName("addon_name")
            @Expose
            public String addon_name;

            @SerializedName("kot_number")
            @Expose
            public String kot_number;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSales_id() {
                return sales_id;
            }

            public void setSales_id(String sales_id) {
                this.sales_id = sales_id;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getAddon_id() {
                return addon_id;
            }

            public void setAddon_id(String addon_id) {
                this.addon_id = addon_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getAddon_name() {
                return addon_name;
            }

            public void setAddon_name(String addon_name) {
                this.addon_name = addon_name;
            }

            public String getKot_number() {
                return kot_number;
            }

            public void setKot_number(String kot_number) {
                this.kot_number = kot_number;
            }
        }

    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany_website() {
        return company_website;
    }

    public void setCompany_website(String company_website) {
        this.company_website = company_website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getSales_date() {
        return sales_date;
    }

    public void setSales_date(String sales_date) {
        this.sales_date = sales_date;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(String tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public String getTax_vale() {
        return tax_vale;
    }

    public void setTax_vale(String tax_vale) {
        this.tax_vale = tax_vale;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

}
