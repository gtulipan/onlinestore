package com.onlineshop.productservice.controller;

import com.onlineshop.productservice.model.ProductDto;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
// @ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

//    @Test //FIXME
    void getAllProductsShouldReturnListOfProducts() {
        webTestClient.get().uri("/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductDto.class)
                .value(products -> assertThat(products).isNotEmpty());
    }
}

