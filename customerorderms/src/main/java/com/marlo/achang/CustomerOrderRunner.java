package com.marlo.achang;

import com.marlo.achang.interfaces.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class CustomerOrderRunner {
  @Autowired OrderRepository orderRepository;

  public static void main(String[] args) {
    SpringApplication.run(CustomerOrderRunner.class);
  }

  @Bean
  @LoadBalanced
  public RestTemplate loadTemplate() {
    return new RestTemplate();
  }

  @Primary
  @Bean
  @LoadBalanced
  public RestTemplate newRestTemplate() {
    return new RestTemplate();
  }
}
