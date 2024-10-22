package com.onlineshop.productservice.controller;

import com.onlineshop.productservice.model.ProductDto;
import com.onlineshop.productservice.service.impl.ProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Api(value = "Product Service")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @ApiOperation(value = "Get all products", notes = "Retrieves a list of all products")
    @GetMapping("/v1/products")
    public Flux<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }
}
