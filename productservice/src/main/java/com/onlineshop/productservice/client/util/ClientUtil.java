package com.onlineshop.productservice.client.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ClientUtil {

    private static final String BEARER = "Bearer ";
    private static final String EMPTY_STRING = "";

    /**
     * Kicsomagolja a tokent az authorization fejlécből.
     * @param exchange {@link ServerWebExchange}
     * @return kicsomagolt token
     */
    public Mono<String> extractToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION)
        ).map(authHeader -> authHeader.replace(BEARER, EMPTY_STRING));
    }
}
