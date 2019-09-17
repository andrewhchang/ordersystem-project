package com.marlo.achang.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

  @Column private String description;

  @Column private int quantity;

  @Column private boolean orderStatusFulfilled = false;
}
