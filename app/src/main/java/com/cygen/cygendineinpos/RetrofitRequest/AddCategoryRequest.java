package com.cygen.cygendineinpos.RetrofitRequest;

public class AddCategoryRequest {

    String user_id, outlet_id, category_name, product_img, category_id, subcategory_name, title_banner,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle_banner(String title_banner) {
        this.title_banner = title_banner;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }
}
