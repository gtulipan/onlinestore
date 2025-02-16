package com.onlinestore.auth2.controller;

import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class CsrfController {

    private final ServerCsrfTokenRepository csrfTokenRepository;

    public CsrfController(ServerCsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @GetMapping("/csrf-token")
    public Mono<CsrfToken> csrfToken(ServerWebExchange exchange) {
        return this.csrfTokenRepository.generateToken(exchange)
                .doOnNext(token -> this.csrfTokenRepository.saveToken(exchange, token).subscribe())
                .map(token -> (CsrfToken) token);
    }
}


