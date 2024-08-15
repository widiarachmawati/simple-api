package com.simple_api.repository;

import com.simple_api.entity.PlaceOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOrderRepository extends JpaRepository<PlaceOrderEntity, String> {
}
