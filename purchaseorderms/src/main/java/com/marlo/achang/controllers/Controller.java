package com.marlo.achang.controllers;

import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.entities.Orderline;
import com.marlo.achang.entities.PurchaseOrder;
import com.marlo.achang.interfaces.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class Controller {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private
    PurchaseOrderRepository purchaseOrderRepository;

    @PostMapping("/distribute")
    private void sendPurchaseOrders(@RequestBody CustomerOrder order){
        List<Orderline> productList = order.getOrderLines();
        for (Orderline orderLine : productList){
            PurchaseOrder purchaseOrder = new PurchaseOrder(orderLine);
            purchaseOrderRepository.save(purchaseOrder);
            log.info("Purchase Order ID{} sent.", purchaseOrder.getPurchaseOrderId());
        }
    }
}
