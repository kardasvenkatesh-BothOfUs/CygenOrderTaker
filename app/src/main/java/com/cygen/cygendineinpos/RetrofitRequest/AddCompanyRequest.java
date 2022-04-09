package com.cygen.cygendineinpos.RetrofitRequest;

public class AddCompanyRequest {

    String user_id,outlet_id,company_website,company_name,mobile,phone,email,country,state,city,address,postcode,bank_details,product_img,id;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public void setCompany_website(String company_website) {
        this.company_website = company_website;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setBank_details(String bank_details) {
        this.bank_details = bank_details;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }
}
