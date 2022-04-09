package com.cygen.cygendineinpos.RetrofitRequest;

public class TableShiftRequest {

    String user_id,outlet_id,table_source,table_destination,order_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getTable_source() {
        return table_source;
    }

    public void setTable_source(String table_source) {
        this.table_source = table_source;
    }

    public String getTable_destination() {
        return table_destination;
    }

    public void setTable_destination(String table_destination) {
        this.table_destination = table_destination;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
