package com.simple_api.repository;

import com.simple_api.entity.OrderCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCartEntity, String> {
  List<OrderCartEntity> findByCustomer(String customer);
}
