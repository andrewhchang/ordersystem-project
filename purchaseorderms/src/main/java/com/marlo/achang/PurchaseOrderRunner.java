package com.marlo.achang;

import com.marlo.achang.wsimport.SoapServerService;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PurchaseOrderRunner {
  public static void main(String[] args) {
    SpringApplication.run(PurchaseOrderRunner.class);
  }

  @Primary
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @LoadBalanced
  public RestTemplate loadTemplate() {
    return new RestTemplate();
  }

  @Bean
  public Queue newQueue() {
    return new Queue("Updated-Orders", false);
  }

  @Bean
  public SoapServerService soapServiceService() {
    return new SoapServerService();
  }
}
