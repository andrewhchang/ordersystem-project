package com.marlo.achang.interfaces;

import com.marlo.achang.entities.CustomerOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {
  List<CustomerOrder> findByCustomerName(String name);
}
