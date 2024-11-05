package com.onlineshop.productservice.service;

import com.onlineshop.productservice.model.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getAllProducts();

    Mono<ProductDto> getProductById(Long id);

    Mono<ProductDto> saveNewProduct(ProductDto productDto);

    Mono<ProductDto> updateProduct(ProductDto productDto, Long id);

    Mono<Void> deleteProductById(Long id);
}
