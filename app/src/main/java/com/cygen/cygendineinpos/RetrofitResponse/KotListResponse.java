package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KotListResponse {

    @SerializedName("status")
    @Expose
    public String status;


    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("customer")
    @Expose
    public String customer;

    @SerializedName("table")
    @Expose
    public String table;

    @SerializedName("time")
    @Expose
    public String time;

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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("kot_list")
    @Expose
    public List<KotListResponseListResponse> kotResults;

    public List<KotListResponseListResponse> getkotResults() {
        return kotResults;
    }

    public void setProductResults(List<KotListResponseListResponse> kotResults) {
        this.kotResults = kotResults;
    }

    public static class KotListResponseListResponse {

        @SerializedName("sales_id")
        @Expose
        public String sales_id;

        @SerializedName("kot_number")
        @Expose
        public String kot_number;

        @SerializedName("table_number")
        @Expose
        public String table_number;

        @SerializedName("order_numbers")
        @Expose
        public String order_numbers;

        @SerializedName("add_time")
        @Expose
        public String add_time;

        @SerializedName("customer")
        @Expose
        public String customer;

        public String getSales_id() {
            return sales_id;
        }

        public void setSales_id(String sales_id) {
            this.sales_id = sales_id;
        }

        public String getKot_number() {
            return kot_number;
        }

        public void setKot_number(String kot_number) {
            this.kot_number = kot_number;
        }

        public String getTable_number() {
            return table_number;
        }

        public void setTable_number(String table_number) {
            this.table_number = table_number;
        }

        public String getOrder_numbers() {
            return order_numbers;
        }

        public void setOrder_numbers(String order_numbers) {
            this.order_numbers = order_numbers;
        }


        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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

            @SerializedName("food_status")
            @Expose
            public String food_status;

            @SerializedName("sales_id")
            @Expose
            public String sales_id;


            @SerializedName("id")
            @Expose
            public String id;

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

            @SerializedName("total_cost")
            @Expose
            public String total_cost;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }


            public String getFood_status() {
                return food_status;
            }

            public void setFood_status(String food_status) {
                this.food_status = food_status;
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

            public String getSales_qty() {
                return sales_qty;
            }

            public void setSales_qty(String sales_qty) {
                this.sales_qty = sales_qty;
            }

            public String getUnit_total_cost() {
                return unit_total_cost;
            }

            public void setUnit_total_cost(String unit_total_cost) {
                this.unit_total_cost = unit_total_cost;
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


            @SerializedName("addonlist")
            @Expose
            public List<AddonsListResponseListResponse> addonsResults;

            public List<AddonsListResponseListResponse> getaddonsResults() {
                return addonsResults;
            }

            public void setProductResults(List<AddonsListResponseListResponse> addonsResults) {
                this.addonsResults = addonsResults;
            }

            public static class AddonsListResponseListResponse {

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

                @SerializedName("waiter_id")
                @Expose
                public String waiter_id;

                @SerializedName("addon_name")
                @Expose
                public String addon_name;

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

                public String getWaiter_id() {
                    return waiter_id;
                }

                public void setWaiter_id(String waiter_id) {
                    this.waiter_id = waiter_id;
                }

                public String getAddon_name() {
                    return addon_name;
                }

                public void setAddon_name(String addon_name) {
                    this.addon_name = addon_name;
                }
            }
        }
    }
}
