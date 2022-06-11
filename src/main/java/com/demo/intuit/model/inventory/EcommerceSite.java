package com.demo.intuit.model.inventory;

import java.util.List;

public class EcommerceSite {
    long storeId;
    String storeName;
    List<Product> storeProductsList;

    public EcommerceSite() {

    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Product> getStoreProductsList() {
        return storeProductsList;
    }

    public void setStoreProductsList(List<Product> storeProductsList) {
        this.storeProductsList = storeProductsList;
    }
}
