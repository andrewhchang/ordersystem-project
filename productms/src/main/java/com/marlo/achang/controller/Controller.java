package com.marlo.achang.controller;

import com.marlo.achang.entities.Product;
import com.marlo.achang.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/products")
public class Controller {
  @Autowired private ProductRepository productRepository;
  @Autowired private RestTemplate restTemplate;
  private ArrayList<String> orderList;

  @RequestMapping("/all")
  private List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @RequestMapping("/{supplierName}")
  private List<Product> getProductsbySupplier(@PathVariable String supplierName) {
    return productRepository.findByProductSuppliers_supplierNameIgnoreCase(supplierName);
  }

  @PostMapping("/validate")
  private ResponseEntity validateProduct(@RequestBody String orderProducts) {
    orderList = new ArrayList<>();
    // remove enclosing square brackets
    String products = orderProducts.substring(1, orderProducts.length() - 1);
    log.info("Order payload: {}", products);
    // string to json array, grab key "orderLines"
    JSONObject object = new JSONObject(products);
    JSONArray keys = object.getJSONArray("orderLines");

    // grab product from each orderline and validate if exists, add to order list
    for (int i = 0; i < keys.length(); i++) {
      JSONObject orderline = keys.getJSONObject(i);
      String productName = orderline.get("productDescription").toString();

      if (productRepository.findByProductName(productName) == null) {
        log.info("Request for product {} returned null", productName);
      } else {
        log.info("Product {} found in Product Database", productName);
        orderList.add(productName);
      }
    }
    ResponseEntity responseEntity;
    if (orderList.isEmpty()) {
      log.info("Products found: {}", orderList.size());
      responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
    } else {
      log.info("Products found: {}", orderList.size());
      responseEntity = new ResponseEntity(HttpStatus.CREATED);
    }
    return responseEntity;
  }
}
