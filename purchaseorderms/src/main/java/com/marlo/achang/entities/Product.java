package com.marlo.achang.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id @GeneratedValue private int productId;
  @Column private String productName;

  @JsonIgnore
  @ManyToMany(mappedBy = "supplierProducts")
  private List<Supplier> productSuppliers;

  public Product(String productName) {
    this.productName = productName;
  }
}
