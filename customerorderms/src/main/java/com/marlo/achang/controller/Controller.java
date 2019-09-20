package com.marlo.achang.controller;

import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.interfaces.OrderRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
  @Autowired @LoadBalanced private RestTemplate loadTemplate;

  @Value("${productservice.api}")
  private String productService;

  @Value("${purchaseorderservice.api}")
  private String purchaseOrderService;

  @RequestMapping("/all")
  private List<CustomerOrder> getAllOrders() {
    return orderRepository.findAll();
  }

  @PostMapping("/submit")
  private ResponseEntity submitOrder(@RequestBody CustomerOrder order) {
    ResponseEntity response =
        restTemplate.postForEntity(productService + "validate", order, ResponseEntity.class);
    if (response.getStatusCode().equals(HttpStatus.CREATED)) {
      log.info("Saving Order ID{}...", order.getOrderId());
      orderRepository.save(order);
      log.info("Sending order ID{}...", order.getOrderId());
      sendOrder(order);
      log.info("Order ID{} sent.", order.getOrderId());
    } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
      log.debug("No products found in stock.{}", "");
    }
    return response;
  }

  private ResponseEntity sendOrder(CustomerOrder order) {
    loadTemplate.postForEntity(purchaseOrderService + "distribute", order, ResponseEntity.class);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
