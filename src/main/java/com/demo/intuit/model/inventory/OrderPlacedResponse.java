package com.demo.intuit.model.inventory;

import java.util.List;

public class OrderPlacedResponse {
    long storeId;
    List<OrderDetails> orderDetailsList;

    public OrderPlacedResponse() {

    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
