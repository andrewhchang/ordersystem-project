package com.marlo.achang.repositories;

import com.marlo.achang.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  List<Product> findByProductSuppliers_supplierNameIgnoreCase(String supplierName);
  Product findByProductName(String orderedProduct);
}
