package com.onlineshop.productservice.service;

import com.onlineshop.productservice.model.ProductDto;
import reactor.core.publisher.Flux;

public interface ProductService {
    Flux<ProductDto> getAllProducts();
}
