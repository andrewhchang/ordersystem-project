package com.marlo.achang.entities.productms;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int supplierId;

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
