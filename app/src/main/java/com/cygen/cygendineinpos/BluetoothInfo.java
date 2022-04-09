package com.cygen.cygendineinpos;

public class BluetoothInfo {
    private String mac;
    private String name;

    public String getName() {
        return this.name;
    }

    public String getMac() {
        return this.mac;
    }

    public BluetoothInfo(String name2, String mac2) {
        this.name = name2;
        this.mac = mac2;
    }
}
