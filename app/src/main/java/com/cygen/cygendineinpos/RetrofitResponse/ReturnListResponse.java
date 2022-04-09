package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReturnListResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;


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

    @SerializedName("return_list")
    @Expose
    public List<ReturnResponse> returnResults;

    public List<ReturnResponse> getreturnResults() {
        return returnResults;
    }

    public void setProductResults(List<ReturnResponse> returnResults) {
        this.returnResults = returnResults;
    }

    public static class ReturnResponse {

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("sales_id")
        @Expose
        public String sales_id;

        @SerializedName("return_code")
        @Expose
        public String return_code;

        @SerializedName("reference_no")
        @Expose
        public String reference_no;

        @SerializedName("return_date")
        @Expose
        public String return_date;

        @SerializedName("return_status")
        @Expose
        public String return_status;

        @SerializedName("order_type")
        @Expose
        public String order_type;

        @SerializedName("grand_total")
        @Expose
        public String grand_total;

        @SerializedName("customer_name")
        @Expose
        public String customer_name;

        @SerializedName("mobile")
        @Expose
        public String mobile;

        @SerializedName("system_name")
        @Expose
        public String system_name;

        public String getSystem_name() {
            return system_name;
        }

        public String getId() {
            return id;
        }

        public String getSales_id() {
            return sales_id;
        }

        public String getReturn_code() {
            return return_code;
        }

        public String getReference_no() {
            return reference_no;
        }

        public String getReturn_date() {
            return return_date;
        }

        public String getGrand_total() {
            return grand_total;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public String getMobile() {
            return mobile;
        }

        public String getReturn_status() {
            return return_status;
        }

        public void setReturn_status(String return_status) {
            this.return_status = return_status;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setSales_id(String sales_id) {
            this.sales_id = sales_id;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public void setReference_no(String reference_no) {
            this.reference_no = reference_no;
        }

        public void setReturn_date(String return_date) {
            this.return_date = return_date;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public void setGrand_total(String grand_total) {
            this.grand_total = grand_total;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setSystem_name(String system_name) {
            this.system_name = system_name;
        }

        @SerializedName("item_list")
        @Expose
        public List<ItemsListResponseListResponse> itemsResults;

        public List<ItemsListResponseListResponse> getitemsResults() {
            return itemsResults;
        }

        public void setProductResults(List<ItemsListResponseListResponse> itemResults) {
            this.itemsResults = itemResults;
        }

        public static class ItemsListResponseListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("sales_id")
            @Expose
            public String sales_id;

            @SerializedName("item_id")
            @Expose
            public String item_id;

            @SerializedName("item_name")
            @Expose
            public String item_name;

            @SerializedName("return_qty")
            @Expose
            public String return_qty;

            @SerializedName("price_per_unit")
            @Expose
            public String price_per_unit;

            @SerializedName("discount_amt")
            @Expose
            public String discount_amt;

            @SerializedName("total_cost")
            @Expose
            public String total_cost;

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

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getReturn_qty() {
                return return_qty;
            }

            public void setReturn_qty(String return_qty) {
                this.return_qty = return_qty;
            }

            public String getPrice_per_unit() {
                return price_per_unit;
            }

            public void setPrice_per_unit(String price_per_unit) {
                this.price_per_unit = price_per_unit;
            }

            public String getTotal_cost() {
                return total_cost;
            }

            public void setTotal_cost(String total_cost) {
                this.total_cost = total_cost;
            }


            public String getDiscount_amt() {
                return discount_amt;
            }



        }
    }
}
