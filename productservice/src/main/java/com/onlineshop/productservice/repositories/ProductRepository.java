package com.onlineshop.productservice.repositories;

import com.onlineshop.productservice.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository  extends ReactiveCrudRepository<Product, Long> {
}
