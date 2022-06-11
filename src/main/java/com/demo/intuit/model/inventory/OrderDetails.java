package com.demo.intuit.model.inventory;

import java.util.Date;

public class OrderDetails {
    long orderId;
    long storeId;
    long productId;
    long quantity;
    Date orderPlacedTime;
    double cost;
    Status status;

    public OrderDetails() {

    }

    public OrderDetails(long storeId, long productId, long quantity, Date orderPlacedTime, double cost, Status status) {
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderPlacedTime = orderPlacedTime;
        this.cost = cost;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Date getOrderPlacedTime() {
        return orderPlacedTime;
    }

    public void setOrderPlacedTime(Date orderPlacedTime) {
        this.orderPlacedTime = orderPlacedTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
