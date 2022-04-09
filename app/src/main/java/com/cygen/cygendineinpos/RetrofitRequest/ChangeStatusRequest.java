package com.cygen.cygendineinpos.RetrofitRequest;

public class ChangeStatusRequest {

    String user_id, outlet_id, id, status, change_table;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChange_table(String change_table) {
        this.change_table = change_table;
    }
}
