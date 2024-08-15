package com.simple_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_cart")
public class OrderCartEntity {
  @Id
  private String orderCartId;
  private String customer;
  private String address;
  private String productId;
  private Integer quantity;
}
