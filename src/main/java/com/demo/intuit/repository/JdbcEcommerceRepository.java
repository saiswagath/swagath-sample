package com.demo.intuit.repository;

import com.demo.intuit.exception.CraftDemoServicesException;
import com.demo.intuit.model.election.Citizen;
import com.demo.intuit.model.inventory.*;
import com.sun.media.sound.DataPusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class JdbcEcommerceRepository implements EcommerceRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public EcommerceSite getProductsForStore(Long storeId) {
        if (storeId == null || storeId <= 0) {
            throw new CraftDemoServicesException("Invalid storeId given in JdbcEcommerceRepository::getProductsForStore()");
        }
        try {
            EcommerceSite ecommerceSite = jdbcTemplate.queryForObject("SELECT * FROM ECOMMERCESTORE WHERE store_id=?",
                    BeanPropertyRowMapper.newInstance(EcommerceSite.class), storeId);

            String productQuery = "select P.product_id, P.product_name,P.price,P.total_quantity,P.available_quantity,S.status_id, S.status_name from PRODUCT P, STORE_PRODUCT_MAPPING SPM, STATUS S "
                    + "WHERE P.product_id = SPM.product_id "
                    + "AND   P.status_id = S.status_id "
                    + "AND   P.status_id = 1 "
                    + "AND   SPM.store_id =" + storeId;

            List<Product> productList = jdbcTemplate.query(productQuery, (result, rowNum) -> new Product(result.getInt("product_id"),
                    result.getString("product_name"), result.getDouble("price"), result.getInt("total_quantity"), result.getInt("available_quantity"), new Status(result.getInt("status_id"), result.getString("status_name"))));

            if (productList != null && productList.size() > 0) {
                ecommerceSite.setStoreProductsList(productList);
            }
            return ecommerceSite;
        }catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while fetching products for storeId " + storeId + "in JdbcEcommerceRepository::getProductsForStore() due to " + e.getMessage());
        }
    }

    @Override
    public int deleteSPMById(Long SPMId) {
        if (SPMId == null || SPMId <= 0) {
            throw new CraftDemoServicesException("Invalid productId given in JdbcEcommerceRepository::deleteProductById()");
        }
        try {
            int deleteRespNum = jdbcTemplate.update("DELETE FROM STORE_PRODUCT_MAPPING WHERE store_product_record_id = ?", SPMId);
            if(deleteRespNum == 0){
                throw new CraftDemoServicesException("No STORE_PRODUCT_MAPPING Found to delete: "+SPMId);
            }
            return deleteRespNum;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while deleting products for SPMId" + SPMId + "in JdbcEcommerceRepository::deleteProductById() due to " + e.getMessage());
        }
    }

    @Override
    public int updateProductName(Product product) {
        String updateQuery = "UPDATE PRODUCT SET product_name=? WHERE product_id =?";
        try {
            return jdbcTemplate.update(updateQuery, new Object[] {product.getProductName(),product.getProductId()});
        }catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while updating product with product_id: " + product.getProductId() + "in JdbcEcommerceRepository::updateProductName() due to " + e.getMessage());
        }
    }

    @Override
    public OrderPlacedResponse processOrders(SyncOrderResponse orderResponse) {
        if(orderResponse.getStoreId() == null || orderResponse.getStoreId() <= 0 || orderResponse.getProductList() == null || orderResponse.getProductList().size() == 0){
            throw new CraftDemoServicesException("Invalid storeId given or product list is empty in JdbcEcommerceRepository::processOrders()");
        }
        try {
            String productIdFilter = " ( ";
            Map<Long, Product> productMap = new HashMap<>();
            List<Product> productList = orderResponse.getProductList();

            for(int i = 0; i < productList.size(); i++){
                if(!productMap.containsKey(productList.get(i).getProductId())){
                    productMap.put(productList.get(i).getProductId(), productList.get(i));
                }

                if(i == productList.size() - 1){
                    productIdFilter += productList.get(i).getProductId() + ") ";
                }else {
                    productIdFilter += productList.get(i).getProductId() + ",";
                }
            }

            String productQuery = "select P.product_id, P.product_name,P.price,P.total_quantity,P.available_quantity,S.status_id, S.status_name from PRODUCT P, STORE_PRODUCT_MAPPING SPM, STATUS S "
                    + "WHERE P.product_id = SPM.product_id "
                    + "AND   P.status_id = S.status_id "
                    + "AND   P.status_id = 1 "
                    + "AND   P.locked_for_store is NULL "
                    + "AND   P.available_quantity > 0 "
                    + "AND   SPM.store_id =" + orderResponse.getStoreId()
                    + " AND   P.product_id IN " +productIdFilter
                    + " FOR UPDATE";

            List<Product> productListResp = jdbcTemplate.query(productQuery, (result, rowNum) -> new Product(result.getInt("product_id"),
                    result.getString("product_name"), result.getDouble("price"), result.getInt("total_quantity"), result.getInt("available_quantity"), new Status(result.getInt("status_id"), result.getString("status_name"))));


            Long currentStoreId = orderResponse.getStoreId();
            OrderPlacedResponse orderPlacedResponse = new OrderPlacedResponse();
            orderPlacedResponse.setStoreId(currentStoreId);

            List<OrderDetails> orderDetailsList = new ArrayList<>();

            for(Product product : productListResp){

                if(productMap.containsKey(product.getProductId())){
                    Product requestProduct = productMap.get(product.getProductId());
                    if(product.getAvailableQuantity() >= requestProduct.getRequiredQuantity() && product.getAvailableQuantity() - requestProduct.getRequiredQuantity() >= 0){
                        OrderDetails orderDetails = new OrderDetails(currentStoreId, product.getProductId(),requestProduct.getRequiredQuantity(),getCurrentDate(),(product.getPrice() * requestProduct.getRequiredQuantity()),product.getStatus());
                        product.setAvailableQuantity(product.getAvailableQuantity() - requestProduct.getRequiredQuantity());
                        int updateRes = updateQuantity(product);
                        orderDetails.setOrderId(placeOrder(orderDetails));
                        orderDetailsList.add(orderDetails);
                    }

                }
            }
            orderPlacedResponse.setOrderDetailsList(orderDetailsList);
            return orderPlacedResponse;
        }catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred in JdbcEcommerceRepository::processOrders() due to " + e.getMessage());
        }
    }


    public Date getCurrentDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat  simpleformat = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
        return simpleformat.parse(simpleformat.format(cal.getTime()));

    }

    public int updateQuantity(Product product) {
        String updateQuery = "UPDATE PRODUCT SET available_quantity=? WHERE product_id =?";
        try {
            return jdbcTemplate.update(updateQuery, new Object[] {product.getAvailableQuantity(),product.getProductId()});
        }catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while updating product with product_id: " + product.getProductId() + "in JdbcEcommerceRepository::updateQuantity() due to " + e.getMessage());
        }
    }


    public Long placeOrder(OrderDetails orderDetails) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO ORDERDETAILS (store_id, product_id, quantity, order_placed_time, cost, status_id) VALUES(?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, orderDetails.getStoreId());
                    statement.setLong(2, orderDetails.getProductId());
                    statement.setLong(3,orderDetails.getQuantity());
                    statement.setDate(4,new java.sql.Date(orderDetails.getOrderPlacedTime().getTime()));
                    statement.setDouble(5, orderDetails.getCost());
                    statement.setLong(6, orderDetails.getStatus().getStatusId());
                    return statement;
                }
            }, holder);
            long orderGenId = holder.getKey().longValue();
            return orderGenId;
        } catch (Exception e) {
            throw new CraftDemoServicesException("Exception occurred while inserting order in JdbcEcommerceRepository::placeOrder() due to " + e.getMessage());
        }
    }
}
