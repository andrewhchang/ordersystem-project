package com.marlo.achang.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Listener {
  @Autowired private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = "Updated-Orders")
  public void listen(Message message) {}
}
