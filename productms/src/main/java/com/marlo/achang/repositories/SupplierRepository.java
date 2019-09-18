package com.marlo.achang.repositories;

import com.marlo.achang.entities.Product;
import com.marlo.achang.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findBySupplierProductsContaining(Product orderProduct);
}
