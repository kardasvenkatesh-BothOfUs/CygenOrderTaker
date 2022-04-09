package com.cygen.cygendineinpos.RetrofitRequest;

public class CategoryRequest {

    String outlet_id;
    String cat_id;
    String user_id;
    String all_list;

    public String getAll_list() {
        return all_list;
    }

    public void setAll_list(String all_list) {
        this.all_list = all_list;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
