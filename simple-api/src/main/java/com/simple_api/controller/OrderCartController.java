package com.simple_api.controller;

import com.simple_api.controller.model.PlaceOrder;
import com.simple_api.controller.model.request.OrderCartRequest;
import com.simple_api.controller.model.response.OrderCartResponse;
import com.simple_api.controller.model.response.PlaceOrderResponse;
import com.simple_api.entity.OrderCartEntity;
import com.simple_api.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RequestMapping(value = "/order-cart")
@RestController
public class OrderCartController {
  private final OrderCartService orderCartService;

  @Autowired
  public OrderCartController(OrderCartService orderCartService) {
    this.orderCartService = orderCartService;
  }

  @PostMapping(value = "/create")
  public ResponseEntity<OrderCartEntity> createOrderCart(@Valid @RequestBody OrderCartRequest request) {
    return ResponseEntity.ok(orderCartService.insertProductToOrderCart(request));
  }

  @GetMapping(value = "/get-order-cart")
  public ResponseEntity<OrderCartResponse> getByCustomer(
      @RequestParam(name = "customer") @NotEmpty String customer) {
    return ResponseEntity.ok(orderCartService.getDataOrderCartByCustomer(customer));
  }

  @PostMapping(value = "/create-order-place")
  public ResponseEntity<PlaceOrderResponse> insertPlaceOrder(@Valid @RequestBody PlaceOrder request) {
    return ResponseEntity.ok(orderCartService.insertOrderCartToPlaceOrder(request));
  }
}
