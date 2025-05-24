package com.onlineshop.productservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Az aut2 mikroszolgáltatás ClientConfig osztályában beállított RegisteredClient -nek megfelelően
 * kéri el a machine-to-machine tokent.
 */
@Component
@RequiredArgsConstructor
public class ServiceTokenProvider {

    //TODO ezekbet kivezetni környezeti változókba!!
    public static final String AUTH2_URL = "http://localhost:8083/oauth2/token";
    public static final String PRODUCT_SERVICE = "product-service";
    public static final String PASSWORD = "secret1235";

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String SCOPE = "scope";
    private static final String INTERNAL = "internal";
    private static final String ACCESS_TOKEN = "access_token";

    private final WebClient.Builder webClientBuilder;

    public Mono<String> getServiceToken() {
        return webClientBuilder.build()
                .post()
                .uri(AUTH2_URL)
                .headers(headers -> headers.setBasicAuth(PRODUCT_SERVICE, PASSWORD))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(GRANT_TYPE, CLIENT_CREDENTIALS)
                        .with(SCOPE, INTERNAL))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get(ACCESS_TOKEN).asText());
    }
}
