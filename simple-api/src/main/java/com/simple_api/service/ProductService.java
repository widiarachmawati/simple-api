package com.simple_api.service;

import com.simple_api.controller.model.Product;
import com.simple_api.entity.ProductEntity;
import com.simple_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public ProductEntity insertProduct(Product request) {
    return productRepository.save(ProductEntity.builder()
        .productId(request.getProductId())
        .productName(request.getProductName())
        .productType(request.getProductType())
        .productPrice(request.getProductPrice())
        .quantity(request.getQuantity())
        .build());
  }

  public Page<ProductEntity> getAllProduct(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productRepository.findAll(pageable);
  }

  public void deleteByProduct(String productId) {
    productRepository.deleteById(productId);
  }
}
