package com.cygen.cygendineinpos.RetrofitResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddOnsResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("responseCode")
    @Expose
    public String responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("productid")
    @Expose
    public String productid;

    @SerializedName("categoryId")
    @Expose
    public String categoryId;

    @SerializedName("Itemcode")
    @Expose
    public String Itemcode;

    @SerializedName("salesPrice")
    @Expose
    public String salesPrice;

    @SerializedName("ProductName")
    @Expose
    public String ProductName;

    @SerializedName("item_image")
    @Expose
    public String item_image;


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

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
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

        @SerializedName("minimum_addon")
        @Expose
        public String minimum_addon;

        @SerializedName("maximum_addon")
        @Expose
        public String maximum_addon;


        @SerializedName("modifier_name")
        @Expose
        public String modifiergroupName;

        @SerializedName("modifiergroup_id")
        @Expose
        public String modifiergroup_id;

        public String getMinimum_addon() {
            return minimum_addon;
        }

        public void setMinimum_addon(String minimum_addon) {
            this.minimum_addon = minimum_addon;
        }


        @SerializedName("modifier")
        @Expose
        public List<ProductAddOnsListResponse> productAddOnsResults;

        public class ProductAddOnsListResponse {

            @SerializedName("id")
            @Expose
            public String id;

            @SerializedName("item_id")
            @Expose
            public String item_id;

            @SerializedName("modifier_id")
            @Expose
            public String modifier_id;

            @SerializedName("modifier_name")
            @Expose
            public String modifier_name;

            @SerializedName("modifier_price")
            @Expose
            public String modifier_price;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
