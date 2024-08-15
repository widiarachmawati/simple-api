package com.simple_api.service;

import com.simple_api.controller.model.OrderCart;
import com.simple_api.controller.model.PlaceOrder;
import com.simple_api.controller.model.request.OrderCartRequest;
import com.simple_api.controller.model.response.OrderCartResponse;
import com.simple_api.controller.model.response.PlaceOrderResponse;
import com.simple_api.entity.OrderCartEntity;
import com.simple_api.entity.PlaceOrderEntity;
import com.simple_api.entity.ProductEntity;
import com.simple_api.repository.OrderCartRepository;
import com.simple_api.repository.PlaceOrderRepository;
import com.simple_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderCartService {
  private final OrderCartRepository orderCartRepository;
  private final ProductRepository productRepository;
  private final PlaceOrderRepository placeOrderRepository;

  @Autowired
  public OrderCartService(OrderCartRepository orderCartRepository, ProductRepository productRepository,
                          PlaceOrderRepository placeOrderRepository) {
    this.orderCartRepository = orderCartRepository;
    this.productRepository = productRepository;
    this.placeOrderRepository = placeOrderRepository;
  }

  public OrderCartEntity insertProductToOrderCart(OrderCartRequest request) {
    return orderCartRepository.save(OrderCartEntity.builder()
        .orderCartId(UUID.randomUUID().toString())
        .customer(request.getCustomer())
        .address(request.getAddress())
        .productId(request.getProductId())
        .quantity(request.getQty())
        .build());
  }

  public OrderCartResponse getDataOrderCartByCustomer(String customer) {
    OrderCartResponse orderCartResponse = new OrderCartResponse();
    List<OrderCart> products = new ArrayList<>();
    BigDecimal totalPrice = BigDecimal.ZERO;

    List<OrderCartEntity> orderCartEntities = orderCartRepository.findByCustomer(customer);
    orderCartResponse.setCustomer(orderCartEntities.get(0).getCustomer());
    orderCartResponse.setAddress(orderCartEntities.get(0).getAddress());

    for (OrderCartEntity entity : orderCartEntities) {
      Optional<ProductEntity> productEntity = productRepository.findById(entity.getProductId());
      if (productEntity.isPresent()) {
        BigDecimal total = productEntity.get().getProductPrice().multiply(
            new BigDecimal(entity.getQuantity()));

        OrderCart orderCart = OrderCart.builder()
            .orderCartId(entity.getOrderCartId())
            .productId(productEntity.get().getProductId())
            .productName(productEntity.get().getProductName())
            .productType(productEntity.get().getProductType())
            .productPrice(productEntity.get().getProductPrice())
            .qty(entity.getQuantity())
            .total(total)
            .build();

        totalPrice = totalPrice.add(total);
        products.add(orderCart);
      }
    }

    orderCartResponse.setProducts(products);
    orderCartResponse.setTotal(totalPrice);
    return orderCartResponse;
  }

  public PlaceOrderResponse insertOrderCartToPlaceOrder(PlaceOrder request) {
    try {
      for (String orderCartId : request.getOrderCartId()) {
        placeOrderRepository.save(PlaceOrderEntity.builder()
            .placeOrderId(UUID.randomUUID().toString())
            .orderCartId(orderCartId)
            .build());

        Optional<OrderCartEntity> orderCartEntity = orderCartRepository.findById(orderCartId);
        ProductEntity productEntity = productRepository.findById(orderCartEntity.get().getProductId()).get();

        Integer updateQty = productEntity.getQuantity() - orderCartEntity.get().getQuantity();
        productEntity.setQuantity(updateQty);
        productRepository.save(productEntity);
      }
      return PlaceOrderResponse.builder().responseMessage("Success saving order to place order").build();
    } catch (Exception e) {
      return PlaceOrderResponse.builder().responseMessage("Failed saving order to place order").build();
    }
  }

}
