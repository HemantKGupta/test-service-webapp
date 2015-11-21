package com.test.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by root on 14/11/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Buyer {
    int buyerId;
    String firstName;
    String lastName;
    String email;
    int phone;
    String address;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }
}
