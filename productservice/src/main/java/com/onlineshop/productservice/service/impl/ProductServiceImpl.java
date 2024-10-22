package com.onlineshop.productservice.service.impl;

import com.onlineshop.productservice.service.ProductService;
import com.onlineshop.productservice.mappers.ProductMapper;
import com.onlineshop.productservice.model.ProductDto;
import com.onlineshop.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Service implementation to serving Product's business logic
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .map(productMapper::toProductDto);
    }
}
