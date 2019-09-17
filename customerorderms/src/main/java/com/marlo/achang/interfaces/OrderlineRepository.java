package com.marlo.achang.interfaces;

import com.marlo.achang.entities.Orderline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderlineRepository extends JpaRepository<Orderline, Integer> {}
