package com.marlo.achang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class);
  }

  @Bean
  public RestTemplate newRestTemplate(){
    return new RestTemplate();
  }
}
