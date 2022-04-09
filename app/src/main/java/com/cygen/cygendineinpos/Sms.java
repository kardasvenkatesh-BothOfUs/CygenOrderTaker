package com.cygen.cygendineinpos;

public class Sms {

    private String itemName, qty, priceIncTax, tax, subTotal, status;

    public Sms(String itemName, String qty, String priceIncTax, String tax, String subTotal, String status) {
        this.itemName = itemName;
        this.qty = qty;
        this.priceIncTax = priceIncTax;
        this.tax = tax;
        this.subTotal = subTotal;
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQty() {
        return qty;
    }

    public String getPriceIncTax() {
        return priceIncTax;
    }

    public String getTax() {
        return tax;
    }


    public String getSubTotal() {
        return subTotal;
    }

    public String getStatus() {
        return status;
    }
}
