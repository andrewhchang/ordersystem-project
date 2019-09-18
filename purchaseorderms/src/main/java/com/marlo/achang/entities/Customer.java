package com.marlo.achang.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
  @Id
  @GeneratedValue
  @Column(name = "CUSTOMER_ID")
  private int customerId;

  @Column(name = "CUSTOMER_NAME")
  private String name;

  public Customer(String name) {
    this.name = name;
  }
}
