package com.onlinestore.auth2.config;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CustomCsrfTokenRequestHandler implements ServerCsrfTokenRequestHandler {

    @Override
    public void handle(ServerWebExchange exchange, Mono<CsrfToken> csrfToken) {
        csrfToken.doOnNext(token -> {
            exchange.getResponse().getHeaders().add(token.getHeaderName(), token.getToken());
        }).subscribe();
    }

    @Override
    public Mono<String> resolveCsrfTokenValue(ServerWebExchange exchange, CsrfToken csrfToken) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(csrfToken.getHeaderName()));
    }
}
