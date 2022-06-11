package com.demo.intuit.model.inventory;

import java.util.Date;

public class Product {
    long productId;
    String productName;
    Double price;
    long totalQuantity;
    long availableQuantity;
    long requiredQuantity;
    long lockedForStore;
    Date lastLockedTime;
    Status status;

    public Product(long productId, String productName, Double price, long totalQuantity, long availableQuantity, Status status) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Product() {

    }

    public long getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public long getLockedForStore() {
        return lockedForStore;
    }

    public void setLockedForStore(long lockedForStore) {
        this.lockedForStore = lockedForStore;
    }

    public Date getLastLockedTime() {
        return lastLockedTime;
    }

    public void setLastLockedTime(Date lastLockedTime) {
        this.lastLockedTime = lastLockedTime;
    }
}
