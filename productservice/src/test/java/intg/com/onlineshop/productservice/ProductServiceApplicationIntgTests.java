package com.onlineshop.productservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test", classes = ProductServiceApplication.class)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 8084) // spin up a httpserver in port 8084
//@TestPropertySource //TODO!!
@ContextConfiguration(classes = ProductServiceApplication.class)
public class ProductServiceApplicationIntgTests {

    @Autowired
    WebTestClient webTestClient;

//    @Test
//    void retrieveProductById() {
//        assertTrue(true); //FIXME
//    }
}
