package com.test.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by root on 14/11/15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Item {
    int itemId;
    String name;
    String description;
    int cost;
    int quantity;
    int sellerId;

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
