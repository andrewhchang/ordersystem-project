package com.marlo.achang;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
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
