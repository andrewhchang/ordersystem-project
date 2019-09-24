package com.marlo.achang.controller;

import com.marlo.achang.entities.Product;
import com.marlo.achang.entities.Supplier;
import com.marlo.achang.entities.customerorder.CustomerOrder;
import com.marlo.achang.entities.customerorder.Orderline;
import com.marlo.achang.repositories.ProductRepository;
import com.marlo.achang.repositories.SupplierRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
public class Controller {
  @Autowired private ProductRepository productRepository;
  @Autowired private SupplierRepository supplierRepository;
  private ArrayList<String> orderList;

  @RequestMapping("/all")
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @RequestMapping("/{supplierName}")
  public List<Product> getProductsbySupplier(@PathVariable String supplierName) {
    return productRepository.findByProductSuppliers_supplierNameIgnoreCase(supplierName);
  }

  @PostMapping("/validate")
  public ResponseEntity validateProduct(@RequestBody CustomerOrder order) {
    ResponseEntity responseEntity;
    orderList = new ArrayList<>();
    List<Orderline> orderlines = order.getOrderLines();
    log.debug("Order payload: {}", order.toString());

    for (Orderline line : orderlines) {
      String productName = line.getProductDescription();
      if (productRepository.findByProductName(productName) != null) {
        log.debug("Product {} found in Product Database", productName);
        orderList.add(productName);
      } else {
        log.debug("Request for product {} returned null", productName);
        responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        return responseEntity;
      }
    }
    log.debug("Products ordered: {}", orderList.size());
    responseEntity = new ResponseEntity(HttpStatus.CREATED);
    return responseEntity;
  }

  @PostMapping("/getsupplier")
  public Supplier getSupplier(@RequestBody String orderProduct) {
    return supplierRepository.findBySupplierProductsContaining(getProduct(orderProduct));
  }

  private Product getProduct(String productName) {
    return productRepository.findByProductName(productName);
  }
}
