package com.test.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by root on 21/11/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Order {
    int orderId;
    String orderDate;
    String shippingDate;
    int orderCost;
    String status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    int buyerId;
}
