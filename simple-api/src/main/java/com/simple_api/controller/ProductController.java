package com.simple_api.controller;

import com.simple_api.controller.model.Product;
import com.simple_api.entity.ProductEntity;
import com.simple_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping(value = "/product")
@RestController
public class ProductController {
  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping(value = "/create-and-update")
  public ResponseEntity<ProductEntity> upsertProduct(@Valid @RequestBody Product request) {
    return ResponseEntity.ok(productService.insertProduct(request));
  }

  @GetMapping(value = "/get-all")
  public Page<ProductEntity> getAllProduct(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return productService.getAllProduct(page, size);
  }

  @DeleteMapping("/delete/{productId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String productId) {
    productService.deleteByProduct(productId);
    return ResponseEntity.noContent().build();
  }

}
