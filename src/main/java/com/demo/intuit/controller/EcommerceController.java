package com.demo.intuit.controller;

import com.demo.intuit.model.election.Citizen;
import com.demo.intuit.model.election.Manifesto;
import com.demo.intuit.model.inventory.*;
import com.demo.intuit.repository.EcommerceRepository;
import com.demo.intuit.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class EcommerceController {
    @Autowired
    EcommerceRepository ecommerceRepository;

    @RequestMapping("/getProductsForStore/{storeId}")
    public ResponseEntity<?> getAllCitizens(@PathVariable Long storeId) {
        try {
            EcommerceSite ecommerceSite = ecommerceRepository.getProductsForStore(storeId);
            return new ResponseEntity<>(ecommerceSite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/getDataFromExternalSite")
    public ResponseEntity<?> getDataFromThirdPartyService() {
        String uri = "https://api.agify.io/?name=bella";

        RestTemplate restTemplate = new RestTemplate();
        ExternalAPI result = restTemplate.getForObject(uri, ExternalAPI.class);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping("/deleteSPM/{SPMId}")
    public ResponseEntity<?> deleteSPM(@PathVariable("SPMId") Long SPMId) {
        ecommerceRepository.deleteSPMById(SPMId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateProduct")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        if(product != null){
            ecommerceRepository.updateProductName(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product is null", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/syncOrders")
    public ResponseEntity<?> syncOrders(@RequestBody SyncOrderResponse syncOrderResponse) {
        try {
            OrderPlacedResponse orderPlacedResponse = ecommerceRepository.processOrders(new SyncOrderResponse(syncOrderResponse.getStoreId(), syncOrderResponse.getProductList()));
            return new ResponseEntity<>(orderPlacedResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
