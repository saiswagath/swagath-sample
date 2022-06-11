package com.demo.intuit.model.inventory;

import java.util.List;

public class SyncOrderResponse {
    Long storeId;
    List<Product> productList;

    public SyncOrderResponse() {

    }

    public SyncOrderResponse(Long storeId, List<Product> productList) {
        this.storeId = storeId;
        this.productList = productList;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
