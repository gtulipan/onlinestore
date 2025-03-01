package com.onlinestore.auth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.csrf.*;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class CsrfConfig {

    /**
     * CookieServerCsrfTokenRepository egy sütit hoz létre a CSRF tokennel
     * */
    @Bean
    public ServerCsrfTokenRepository serverCsrfTokenRepository() {
        CookieServerCsrfTokenRepository csrfTokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setHeaderName("XSRF-TOKEN");
        csrfTokenRepository.setParameterName("_csrf");
        return csrfTokenRepository;
    }

    @Bean
    public ServerCsrfTokenRequestHandler serverCsrfTokenRequestHandler() {
        return new CustomCsrfTokenRequestHandler();
//        return new XorServerCsrfTokenRequestAttributeHandler();
    }

    @Bean
    WebFilter csrfCookieWebFilter() {
        return (exchange, chain) -> {
            Mono<CsrfToken> csrfToken = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
            return csrfToken.doOnSuccess(token -> {
            }).then(chain.filter(exchange));
        };
    }

//    @Bean
//    public WebFilter csrfWebFilter(CookieServerCsrfTokenRepository csrfTokenRepository) {
//        return (exchange, chain) -> {
//            Mono<CsrfToken> csrfToken = csrfTokenRepository.generateToken(exchange);
//            exchange.getResponse().getHeaders().add("XSRF-TOKEN", csrfToken.block().getToken());
//            return chain.filter(exchange);
//        };
//    }
}
