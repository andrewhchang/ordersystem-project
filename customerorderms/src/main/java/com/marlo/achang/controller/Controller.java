package com.marlo.achang.controller;

import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.interfaces.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerorder")
public class Controller {
  @Autowired OrderRepository orderRepository;

  @RequestMapping("/all")
  private List<CustomerOrder> getAllOrders() {
    return orderRepository.findAll();
  }
}
