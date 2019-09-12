package com.marlo.achang.controller;

import com.marlo.achang.entities.Product;
import com.marlo.achang.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/products")
public class Controller {
  @Autowired private ProductRepository productRepository;
  @Autowired private RestTemplate restTemplate;

  @RequestMapping("/all")
  private List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @RequestMapping("/supplier/{supplierName}")
  private List<Product> getProductsbySupplier(@PathVariable String supplierName) {
    return productRepository.findByProductSuppliers_supplierNameIgnoreCase(supplierName);
  }
}
