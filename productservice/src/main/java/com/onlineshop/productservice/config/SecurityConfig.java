package com.onlineshop.productservice.config;

import com.onlineshop.productservice.exception.InvalidTokenException;
import com.onlineshop.productservice.model.DecodedJwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.jwt.Jwt;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ROLES_CLAIM = "roles";
    public static final String EMPTY_STRING = "";

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/swagger-ui/**", "/app/**").permitAll()  // Kivételek megadása
//                        .anyExchange().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> {
//                            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
//                            jwt.jwtDecoder(jwtDecoder());
//                        })
//                );
//        return http.build();
//    }

    private final WebClient.Builder webClientBuilder;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/public/**", "/swagger-ui/**", "/app/**",
                                "api/v1/user/login", "api/v1/user/register","webjars/**",
                                "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder())
                        )
                )
                .build();
    }

    /**
     * egy egyedi JWT dekódert használ,
     * amely egy WebClient segítségével ellenőrzi a tokent az autentikációs mikroszolgáltatással.
     * */
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return token -> webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/validateToken")//("http://authentication-service/validateToken")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if (validResponse(response)) {
                        return parseJwtFromResponse(response, token);
                    } else {
                        return Mono.error(new InvalidTokenException("Invalid token"));
                    }
                });
    }

    private boolean validResponse(String response) {
        // Ellenőrizd a válasz érvényességét (pl. HTTP státusz kód, válasz tartalma)
        return true; // Példa; itt adj meg egy tényleges ellenőrzést
    }

    private Mono<Jwt> parseJwtFromResponse(String response, String token) {
        // Parse-oljuk a JWT-t a válaszból
        // Ebben a példában feltételezzük, hogy a válaszban találhatóak a szükséges adatok
        Map<String, Object> claims = new HashMap<>();

        // Példa az adatok feltöltésére; itt használd a tényleges választ
        claims.put("sub", "user");
        claims.put("iss", "issuer");
        claims.put("exp", System.currentTimeMillis() / 1000L + 3600); // 1 óra lejárat

        Consumer<Map<String, Object>> headersConsumer = headers -> {
            headers.put("alg", "HS256");
        };

        Consumer<Map<String, Object>> claimsConsumer = c -> {
            c.putAll(claims);
        };

        Jwt jwt = Jwt.withTokenValue(token)
                .headers(headersConsumer)
                .claims(claimsConsumer)
                .build();

        return Mono.just(jwt);
    }

//    @Bean
//    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
////        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        grantedAuthoritiesConverter.setAuthoritiesClaimName(ROLES_CLAIM);
//        grantedAuthoritiesConverter.setAuthorityPrefix(EMPTY_STRING);
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return NimbusReactiveJwtDecoder.withPublicKey(publicKey()).build();
//    }
//
//    private RSAPublicKey publicKey() {
//        try {
//            String publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(String.join("", "keys", FileSystems.getDefault().getSeparator(), "public_key.pem")).toURI())));
//            publicKeyContent = publicKeyContent
//                    .replaceAll("\\r", EMPTY_STRING)
//                    .replaceAll("\\n", EMPTY_STRING)
//                    .replace("-----BEGIN PUBLIC KEY-----", EMPTY_STRING)
//                    .replace("-----END PUBLIC KEY-----", EMPTY_STRING);
//            byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return (RSAPublicKey) keyFactory.generatePublic(spec);
//        } catch (Exception e) {
//            throw new IllegalStateException("Invalid public key", e);
//        }
//    }
//
//    @Bean
//    public PasswordEncoder bcryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @Bean
//    public ReactiveAuthenticationManager authenticationManager() {
//        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
//                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
//        authenticationManager.setPasswordEncoder(bcryptPasswordEncoder());
//        return authenticationManager;
//    }
}
