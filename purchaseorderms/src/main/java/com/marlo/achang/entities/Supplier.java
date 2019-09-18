package com.marlo.achang.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
  @Id @GeneratedValue private int supplierId;
  @Column private String supplierName;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "supplier_products",
      joinColumns = @JoinColumn(name = "supplier_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> supplierProducts;

  public Supplier(String supplierName, List<Product> ownedProducts) {
    this.supplierName = supplierName;
    this.supplierProducts = ownedProducts;
  }
}
