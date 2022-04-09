package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponse {

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

    @SerializedName("list")
    @Expose
    public List<ProductListResponse> productResults;

    public List<ProductListResponse> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductListResponse> productResults) {
        this.productResults = productResults;
    }

    public static class ProductListResponse {

        @SerializedName("veg")
        @Expose
        public String veg;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("category_id")
        @Expose
        public String category_id;

        @SerializedName("item_code")
        @Expose
        public String item_code;

        @SerializedName("item_name")
        @Expose
        public String item_name;

        @SerializedName("category_name")
        @Expose
        public String category_name;

        @SerializedName("lot_number")
        @Expose
        public String lot_number;

        @SerializedName("unit_name")
        @Expose
        public String unit_name;

        @SerializedName("unit_id")
        @Expose
        public String unit_id;

        @SerializedName("stock")
        @Expose
        public String stock;

        @SerializedName("sales_price")
        @Expose
        public String sales_price;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("product_name")
        @Expose
        public String product_name;

        @SerializedName("product_image")
        @Expose
        public String product_image;

        @SerializedName("product_rating")
        @Expose
        public String product_rating;

        public String getVeg() {
            return veg;
        }

        public void setVeg(String veg) {
            this.veg = veg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getItem_code() {
            return item_code;
        }

        public void setItem_code(String item_code) {
            this.item_code = item_code;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getLot_number() {
            return lot_number;
        }

        public void setLot_number(String lot_number) {
            this.lot_number = lot_number;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getSales_price() {
            return sales_price;
        }

        public void setSales_price(String sales_price) {
            this.sales_price = sales_price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
        }

        public String getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(String product_rating) {
            this.product_rating = product_rating;
        }

        @SerializedName("modifiergroupList")
        @Expose
        public List<ProductAddOnsGroupListResponse> productAddOnsGroupResults;

        public List<ProductAddOnsGroupListResponse> getProductAddOnsGroupResults() {
            return productAddOnsGroupResults;
        }

        public void setProductAddOnsGroupResults(List<ProductAddOnsGroupListResponse> productAddOnsGroupResults) {
            this.productAddOnsGroupResults = productAddOnsGroupResults;
        }

        public class ProductAddOnsGroupListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("maximum_addon")
            @Expose
            public String maximum_addon;

            @SerializedName("item_group_id")
            @Expose
            public String item_group_id;

            @SerializedName("modifier_name")
            @Expose
            public String modifiergroupName;

            @SerializedName("modifiergroup_id")
            @Expose
            public String modifiergroup_id;

            @SerializedName("modifier")
            @Expose
            public List<ProductAddOnsListResponse> productAddOnsResults;

            public class ProductAddOnsListResponse {

                @SerializedName("id")
                @Expose
                public String id;

                @SerializedName("items_modifier_code")
                @Expose
                public String items_modifier_code;

                @SerializedName("item_id")
                @Expose
                public String item_id;

                @SerializedName("modifier_id")
                @Expose
                public String modifier_id;

                @SerializedName("status")
                @Expose
                public String status;

                @SerializedName("modifier_name")
                @Expose
                public String modifier_name;

                @SerializedName("modifier_price")
                @Expose
                public String modifier_price;

                @SerializedName("modifier_image")
                @Expose
                public String modifier_image;


                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getItems_modifier_code() {
                    return items_modifier_code;
                }

                public void setItems_modifier_code(String items_modifier_code) {
                    this.items_modifier_code = items_modifier_code;
                }

                public String getItem_id() {
                    return item_id;
                }

                public void setItem_id(String item_id) {
                    this.item_id = item_id;
                }

                public String getModifier_id() {
                    return modifier_id;
                }

                public void setModifier_id(String modifier_id) {
                    this.modifier_id = modifier_id;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getModifier_name() {
                    return modifier_name;
                }

                public void setModifier_name(String modifier_name) {
                    this.modifier_name = modifier_name;
                }

                public String getModifier_price() {
                    return modifier_price;
                }

                public void setModifier_price(String modifier_price) {
                    this.modifier_price = modifier_price;
                }

                public String getModifier_image() {
                    return modifier_image;
                }

                public void setModifier_image(String modifier_image) {
                    this.modifier_image = modifier_image;
                }
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getItem_group_id() {
                return item_group_id;
            }

            public void setItem_group_id(String item_group_id) {
                this.item_group_id = item_group_id;
            }

            public String getModifiergroupName() {
                return modifiergroupName;
            }

            public void setModifiergroupName(String modifiergroupName) {
                this.modifiergroupName = modifiergroupName;
            }

            public String getModifiergroup_id() {
                return modifiergroup_id;
            }

            public void setModifiergroup_id(String modifiergroup_id) {
                this.modifiergroup_id = modifiergroup_id;
            }

            public String getMaximum_addon() {
                return maximum_addon;
            }

            public void setMaximum_addon(String maximum_addon) {
                this.maximum_addon = maximum_addon;
            }

            public List<ProductAddOnsListResponse> getProductAddOnsResults() {
                return productAddOnsResults;
            }

            public void setProductAddOnsResults(List<ProductAddOnsListResponse> productAddOnsResults) {
                this.productAddOnsResults = productAddOnsResults;
            }
        }

    }

}
