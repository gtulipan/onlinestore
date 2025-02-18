package com.onlinestore.auth2.controller;

import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CsrfController {

    private static final Logger logger = LoggerFactory.getLogger(CsrfController.class);
    private final ServerCsrfTokenRepository csrfTokenRepository;

    public CsrfController(ServerCsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @GetMapping("/csrf-token")
    public Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.generateToken(exchange)
                .doOnNext(token -> {
                    logger.debug("Generated CSRF Token: {}", token.getToken());
                    this.csrfTokenRepository.saveToken(exchange, token).subscribe();
                })
                .map(token -> {
                    logger.debug("Saved CSRF Token: {}", token.getToken());
                    return (CsrfToken) token;
                });
    }
}
