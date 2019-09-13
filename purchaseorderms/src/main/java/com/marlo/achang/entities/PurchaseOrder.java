package com.marlo.achang.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Column
    private String description;

    @Column
    private int quantity;

    @Column
    private boolean orderStatusFulfilled = false;
}
