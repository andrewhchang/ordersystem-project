package com.marlo.achang;

import com.marlo.achang.entities.Customer;
import com.marlo.achang.entities.CustomerOrder;
import com.marlo.achang.entities.Orderline;
import com.marlo.achang.interfaces.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class App {
  @Autowired OrderRepository orderRepository;

  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }

  @Bean
  public CommandLineRunner runner() {
    return args -> {
      Customer customer = new Customer("Customer_A");
      Orderline orderLine_a = new Orderline("Fresh Sandwich", 9);
      Orderline orderLine_b = new Orderline("Batt", 3);
      List<Orderline> orderLines = new ArrayList<>();
      orderLines.add(orderLine_a);
      orderLines.add(orderLine_b);

      CustomerOrder customerOrder = new CustomerOrder(customer, orderLines);
      orderRepository.save(customerOrder);
    };
  }

  @Bean
  public RestTemplate newRestTemplate() {
    return new RestTemplate();
  }
}
