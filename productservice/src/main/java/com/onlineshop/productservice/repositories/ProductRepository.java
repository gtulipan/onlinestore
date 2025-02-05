package com.onlineshop.productservice.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import com.onlineshop.productservice.domain.Product;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {
}
