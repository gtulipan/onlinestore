package com.onlineshop.productservice.service.impl;

import com.onlineshop.productservice.domain.Product;
import com.onlineshop.productservice.mappers.ProductMapper;
import com.onlineshop.productservice.model.ProductDto;
import com.onlineshop.productservice.repositories.ProductRepository;
import com.onlineshop.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service implementation to serving Product's business logic
 */
@Slf4j
@Service
//@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private static final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll() // Non-blocking call
                .map(productMapper::toProductDto);
    }

    @Override
    public Mono<ProductDto> getProductById(Long id) {
        return productRepository.findById(id) // Non-blocking call
                .map(productMapper::toProductDto);
    }

    @Override
    public Mono<ProductDto> saveNewProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        return productRepository.save(product) // Non-blocking call
                .map(productMapper::toProductDto);
    }

    @Override
    public Mono<ProductDto> updateProduct(ProductDto productDto, Long id) {
        return productRepository.findById(id) // Non-blocking call
                .flatMap(product -> {
                    product.setName(productDto.getName());
                    product.setPrice(productDto.getPrice());
                    product.setDescription(productDto.getDescription());
                    return productRepository.save(product); // Non-blocking call
                })
                .map(productMapper::toProductDto);
    }

    @Override
    public Mono<Void> deleteProductById(Long id) {
        return productRepository.deleteById(id); // Non-blocking call
    }


//*************************************************************************************************************//
// Spring Webflux with blocking SQL/JPA calls:
//
//    @Override
//    public Flux<ProductDto> getAllProducts() {
//        List<Product> products = productRepository.findAll(); // Blocking call
//        return Flux.fromIterable(products).map(productMapper::toProductDto); // Convert to reactive stream
//    }
//
//    @Override
//    public Mono<ProductDto> getProductById(Long id) {
//        return Mono.justOrEmpty(
//                productRepository.findById(id) // Blocking call
//                        .map(productMapper::toProductDto));
//    }
//
//    @Override
//    public Mono<ProductDto> saveNewProduct(ProductDto productDto) {
//        Product product = productMapper.toProduct(productDto);
//        return Mono.fromCallable(
//                        () -> productRepository.save(product)) // Blocking call
//                .map(productMapper::toProductDto);
//    }
//
//    @Override
//    public Mono<ProductDto> updateProduct(ProductDto productDto, Long id) {
//        return Mono.fromCallable(() -> productRepository.findById(id)) // Blocking call
//                .flatMap(productMono -> productMono.map(product -> {
//                    product.setName(productDto.getName());
//                    product.setPrice(productDto.getPrice());
//                    product.setDescription(productDto.getDescription());
//                    return Mono.fromCallable(() -> productRepository.save(product)); // Blocking call
//                }).orElse(Mono.empty()))
//                    .map(productMapper::toProductDto);
//    }
// *************************************************************************************************************//
    }
