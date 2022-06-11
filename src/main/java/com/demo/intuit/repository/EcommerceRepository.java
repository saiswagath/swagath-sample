package com.demo.intuit.repository;

import com.demo.intuit.model.inventory.EcommerceSite;
import com.demo.intuit.model.inventory.OrderPlacedResponse;
import com.demo.intuit.model.inventory.Product;
import com.demo.intuit.model.inventory.SyncOrderResponse;

public interface EcommerceRepository {

    EcommerceSite getProductsForStore(Long storeId);
    int deleteSPMById(Long SPMId);
    int updateProductName(Product product);
    OrderPlacedResponse processOrders(SyncOrderResponse orderResponse);
}
