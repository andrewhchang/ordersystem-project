package com.marlo.achang;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RunnerA {
  public static void main(String[] args) {
    SpringApplication.run(RunnerA.class, args);
  }

  @Bean
  public Queue queue() {
    return new Queue("Pending-Orders", false);
  }
}
