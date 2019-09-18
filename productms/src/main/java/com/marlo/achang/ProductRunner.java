package com.marlo.achang;

import com.marlo.achang.entities.Product;
import com.marlo.achang.entities.Supplier;
import com.marlo.achang.repositories.SupplierRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
  public CommandLineRunner runner() {
    return args -> {
      Product productA1 = new Product("Ball");
      Product productA2 = new Product("Bat");
      Product productB1 = new Product("Bandaid");
      Product productB2 = new Product("Cotton Tips");
      Product productC1 = new Product("Cola");
      Product productC2 = new Product("Sandwich");

      List<Product> productListA = new ArrayList<>();
      List<Product> productListB = new ArrayList<>();
      List<Product> productListC = new ArrayList<>();

      Collections.addAll(productListA, productA1, productA2);
      Collections.addAll(productListB, productB1, productB2);
      Collections.addAll(productListC, productC1, productC2);

      Supplier supplierA = new Supplier("Supplier_A", productListA);
      Supplier supplierB = new Supplier("Supplier_B", productListB);
      Supplier supplierC = new Supplier("Supplier_C", productListC);

      supplierRepository.save(supplierA);
      supplierRepository.save(supplierB);
      supplierRepository.save(supplierC);
    };
  }

  @Bean
  @LoadBalanced
  public RestTemplate newRestTemplate() {
    return new RestTemplate();
  }
}