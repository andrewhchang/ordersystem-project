package com.marlo.achang.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
  @Id
  @GeneratedValue
  @Column(name = "PURCHASEORDER_ID")
  private int purchaseOrderId;

  @OneToOne
  @JoinColumn private Orderline orderline;

  @Column private boolean orderStatusFulfilled = false;

  public PurchaseOrder(Orderline orderline){
    this.orderline = orderline;
  }
}
