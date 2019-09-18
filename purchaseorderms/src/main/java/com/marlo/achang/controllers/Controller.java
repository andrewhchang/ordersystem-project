package com.marlo.achang.controllers;

import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.entities.Orderline;
import com.marlo.achang.entities.PurchaseOrder;
import com.marlo.achang.entities.Supplier;
import com.marlo.achang.interfaces.PurchaseOrderRepository;
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
@RequestMapping("/purchaseorder")
public class Controller {
  @Autowired private RestTemplate restTemplate;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  private String productService = "http://product-service/products/";

  @RequestMapping("/all")
  private String tester() {
    return "Test";
  }

  @PostMapping("/distribute")
  private ResponseEntity sendPurchaseOrders(@RequestBody CustomerOrder order) {
    List<Orderline> productList = order.getOrderLines();
    for (Orderline orderLine : productList) {
      PurchaseOrder purchaseOrder = new PurchaseOrder(orderLine);
      purchaseOrderRepository.save(purchaseOrder);
      Supplier supplier =
          restTemplate.postForObject(
              productService + "getsupplier", orderLine.getProductDescription(), Supplier.class);
      log.info(supplier.getSupplierName() + "{}", "");
      log.info("Purchase Order ID{} sent.", purchaseOrder.getPurchaseOrderId());
    }
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
