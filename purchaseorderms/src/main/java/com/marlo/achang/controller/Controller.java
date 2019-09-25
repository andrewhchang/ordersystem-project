package com.marlo.achang.controller;

import com.marlo.achang.entities.PurchaseOrder;
import com.marlo.achang.entities.customerorder.CustomerOrder;
import com.marlo.achang.entities.customerorder.Orderline;
import com.marlo.achang.entities.productms.Supplier;
import com.marlo.achang.interfaces.PurchaseOrderRepository;
import com.marlo.achang.wsimport.SoapServer;
import com.marlo.achang.wsimport.SoapServerService;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

@Setter
@NoArgsConstructor
@Slf4j
@RestController
@RequestMapping("/purchaseorder")
public class Controller {
  @Autowired private RestTemplate restTemplate;
  @Autowired @LoadBalanced RestTemplate loadTemplate;
  @Autowired private PurchaseOrderRepository purchaseOrderRepository;
  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private SoapServerService soapClient;
  private SoapServer soapServer;

  @Value("${productservice.api}")
  private String productService;

  @Value("${supplierb.api}")
  private String supplierBApi;

  @PostMapping("/distribute")
  public ResponseEntity sendPurchaseOrders(@RequestBody CustomerOrder order) {
    List<Orderline> productList = order.getOrderLines();
    ResponseEntity response;
    for (Orderline orderLine : productList) {
      PurchaseOrder purchaseOrder = new PurchaseOrder(orderLine);
      Supplier supplier =
          restTemplate.postForObject(
              productService + "getsupplier", orderLine.getProductDescription(), Supplier.class);
      assert supplier != null;
      purchaseOrderRepository.save(purchaseOrder);

      log.debug("Supplier: {}", supplier.getSupplierName());
      switch (supplier.getSupplierName()) {
        case "Supplier_A":
          sendToSupplierA(purchaseOrder);
          break;
        case "Supplier_B":
          sendToSupplierB(purchaseOrder);
          break;
        case "Supplier_C":
          sendToSupplierC(purchaseOrder);
          break;
        default:
          return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      log.debug("Purchase Order ID{} sent.", purchaseOrder.getPurchaseOrderId());
      purchaseOrder.setOrderStatusFulfilled(true);
      log.debug(
          "Purchase order ID{} fulfilled status: {}",
          purchaseOrder.getPurchaseOrderId(),
          purchaseOrder.isOrderStatusFulfilled());
    }
    return new ResponseEntity(HttpStatus.CREATED);
  }

  public ResponseEntity sendToSupplierA(PurchaseOrder purchaseOrder) {
    rabbitTemplate.convertAndSend("Pending-Orders", purchaseOrder.toString());
    return new ResponseEntity(HttpStatus.OK);
  }

  public ResponseEntity sendToSupplierB(PurchaseOrder purchaseOrder) {
    return loadTemplate.postForEntity(supplierBApi, purchaseOrder, ResponseEntity.class);
  }

  public String sendToSupplierC(PurchaseOrder purchaseOrder) {
    soapServer = soapClient.getSoapServerPort();
    return soapServer.receive(purchaseOrder.toString());
  }

  public Controller(RestTemplate loadTemplate) {
    this.loadTemplate = loadTemplate;
  }
}
