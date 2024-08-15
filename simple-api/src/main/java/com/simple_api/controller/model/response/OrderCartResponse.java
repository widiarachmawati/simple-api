package com.simple_api.controller.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simple_api.controller.model.OrderCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCartResponse {
  private String customer;
  private String address;
  private List<OrderCart> products;
  private BigDecimal total;
}
