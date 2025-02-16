package com.onlineshop.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ProductServiceApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = ProductServiceApplication.class)
class ProductServiceApplicationUnitTests {

    @Test
    void contextLoads() {
    }
}
