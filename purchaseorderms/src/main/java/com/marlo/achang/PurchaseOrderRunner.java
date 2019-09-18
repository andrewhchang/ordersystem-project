package com.marlo.achang;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class PurchaseOrderRunner {
  public static void main(String[] args) {
    SpringApplication.run(PurchaseOrderRunner.class);
  }

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

  @Bean
  public Queue newQueue(){
    return new Queue("Updated-Orders", false);
  }
}

