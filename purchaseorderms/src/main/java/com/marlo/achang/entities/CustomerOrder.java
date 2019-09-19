package com.marlo.achang.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "CUSTOMERORDER_ID")
  private int orderId;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn
  private List<Orderline> orderLines;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CUSTOMER_NAME")
  private Customer customer;

  public CustomerOrder(Customer customer, List<Orderline> orderLines) {
    this.customer = customer;
    this.orderLines = orderLines;
  }
}
