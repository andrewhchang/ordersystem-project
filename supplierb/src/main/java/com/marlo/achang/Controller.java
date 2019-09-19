package com.marlo.achang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootConfiguration
@RequestMapping("/supplierb")
public class Controller {

  @PostMapping("/receive")
  private ResponseEntity receiveOrder(@RequestBody PurchaseOrder order) {
    log.info("Received {}", order.toString());
    return new ResponseEntity(HttpStatus.CREATED);
  }
}
