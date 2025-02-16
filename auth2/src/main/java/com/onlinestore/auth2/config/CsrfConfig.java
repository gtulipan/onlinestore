package com.onlinestore.auth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestHandler;

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
    }
}
