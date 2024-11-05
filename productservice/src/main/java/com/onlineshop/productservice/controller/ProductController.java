package com.onlineshop.productservice.controller;

import com.onlineshop.productservice.exception.ProductNotFoundException;
import com.onlineshop.productservice.model.ProductDto;
import com.onlineshop.productservice.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@OpenAPIDefinition(info = @Info(title = "Product Service", version = "v1"))
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;

    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    @GetMapping("/v1/products")
    public Flux<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product, based on ID")
    @GetMapping("/v1/products/{id}")
    public Mono<ProductDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Post product", description = "Saves a new product")
    @PostMapping("/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDto> saveNewProduct(@RequestBody @Validated ProductDto productDto) {
        return productService.saveNewProduct(productDto);
    }

    @Operation(summary = "Put product", description = "Update a product")
    @PutMapping("/v1/products/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@RequestBody @Validated ProductDto productDto, @PathVariable Long id) {
        return productService.updateProduct(productDto, id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found")))
                .log();
    }
    @Operation(summary = "Delete product", description = "Deletes a product based on ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/v1/products/{id}")
    public Mono<Void> deleteProductById(@PathVariable Long id) {
        return productService.deleteProductById(id)
                .doOnSuccess(unused -> log.info("Deleted product with ID: {}", id))
                .doOnError(error -> log.error("Failed to delete product with ID: {}", id, error))
                .log();
    }

}
