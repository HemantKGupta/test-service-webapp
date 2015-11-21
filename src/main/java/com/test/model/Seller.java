package com.test.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by root on 14/11/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Seller {
    int sellerId;
    String name;
    String email;
    int phone;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
