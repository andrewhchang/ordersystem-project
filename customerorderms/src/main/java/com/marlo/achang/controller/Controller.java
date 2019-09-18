package com.marlo.achang.controller;

import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.interfaces.OrderRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/customerorder")
public class Controller {
  @Autowired private OrderRepository orderRepository;
  @Autowired private RestTemplate restTemplate;

  private String productService = "http://product-service/";

  @RequestMapping("/all")
  private List<CustomerOrder> getAllOrders() {
    return orderRepository.findAll();
  }

  @PostMapping("/send")
  private ResponseEntity sendOrder(@RequestBody CustomerOrder order) {
    ResponseEntity response =
        restTemplate.postForEntity(
            productService + "products/validate", order, ResponseEntity.class);
    if (response.getStatusCode().equals(HttpStatus.CREATED)) {
      log.info("Saving Order...{}", "");
      orderRepository.save(order);
      log.info("Processing order...{}", "");
      // TODO: process order
    } else log.info("No products found in stock.{}", "");
    return response;
  }
}
