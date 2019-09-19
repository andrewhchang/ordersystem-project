package com.marlo.achang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {
  @Autowired private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = "Pending-Orders")
  public void listen(String message) {
    rabbitTemplate.convertAndSend("Updated-Orders", message);
    log.info("Received {} from PurchaseOrderMS.", message);
  }
}
