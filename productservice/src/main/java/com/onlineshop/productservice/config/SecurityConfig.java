package com.onlineshop.productservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ROLES_CLAIM = "roles";
    private static final String EMPTY_STRING = "";
    private static final String ADMIN = "ADMIN";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) //Mivel JWT header van használva és nem cookie, ezért nincs rá szükség
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "webjars/**",
                                "/webjars/swagger-ui/index.html",
                                "/swagger-ui.html",
                                "/public/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/products/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/products/**").hasAnyAuthority(ADMIN)
                        .pathMatchers(HttpMethod.PUT, "/products/**").hasAnyAuthority(ADMIN)
                        .pathMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority(ADMIN)
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .build();
    }

    /**
     * Ennél a jwt decoder megoldásnál a product service közvetlenül a JWK endpointból tölti be a publikus kulcsot,
     * és a Spring Security végzi a token validálást.
     */
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:8083/oauth2/jwks").build();
    }

    /**
     * Ezáltal a roles claim alapján lesznek a jogosultságok kezelve.
     */
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(ROLES_CLAIM);
        grantedAuthoritiesConverter.setAuthorityPrefix(EMPTY_STRING);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


}
