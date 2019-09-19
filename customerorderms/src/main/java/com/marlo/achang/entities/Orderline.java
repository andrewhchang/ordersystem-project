package com.marlo.achang.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orderline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDERLINE_ID")
  private int orderLineId;

  @Column private String productDescription;

  @Column private int quantity;

  public Orderline(String productDescription, int quantity) {
    this.productDescription = productDescription;
    this.quantity = quantity;
  }
}
