package com.marlo.achang.entities.productms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int productId;

  @Column private String productName;

  @JsonIgnore
  @ManyToMany(mappedBy = "supplierProducts")
  private List<Supplier> productSuppliers;

  public Product(String productName) {
    this.productName = productName;
  }
}
