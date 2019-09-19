package com.marlo.achang;

import com.marlo.achang.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProductRunner {

  @Autowired private SupplierRepository supplierRepository;

  public static void main(String[] args) {
    SpringApplication.run(ProductRunner.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate newRestTemplate() {
    return new RestTemplate();
  }
}
