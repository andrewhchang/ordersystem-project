package com.marlo.achang;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PURCHASEORDER_ID")
  private int purchaseOrderId;

  @OneToOne @JoinColumn private Orderline orderline;

  @Column private boolean orderStatusFulfilled = false;

  public PurchaseOrder(Orderline orderline) {
    this.orderline = orderline;
  }

  @Override
  public String toString() {
    return "PurchaseOrder("
        + "purchaseOrderId="
        + this.purchaseOrderId
        + ", "
        + this.orderline.toString()
        + ", orderStatusFulfilled="
        + this.orderStatusFulfilled
        + ")";
  }
}
