package com.onlinestore.auth2.controller;

import com.onlinestore.auth2.model.AuthenticationRequest;
import com.onlinestore.auth2.service.impl.CustomerServiceImpl;
import com.onlinestore.auth2.service.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@OpenAPIDefinition(info = @Info(title = "Auth2 Service", version = "v1"))
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final CustomerServiceImpl customerService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Operation(summary = "POST request for token", description = "Generate new token based on user details.")
    @CrossOrigin(origins = "${angular.client.url}")
    @PostMapping("/v1/login")
    /**
     * Tesztelés. A CsrfController /csrf-token végponton hozatjuk létre a csrf tokent, amivel tesztelni tudjuk a szerver működését.
     * PowerShell-ből:
     * 1. lépés: Invoke-WebRequest -Uri http://localhost:8083/csrf-token -SessionVariable Session | Out-Null
     * 2. lépés: $csrfToken = $Session.Cookies.GetCookies("http://localhost:8083")["XSRF-TOKEN"].Value
     * 3. lépés: $csrfTokenHeader = @{ "X-CSRF-TOKEN" = $csrfToken }
     * 4. lépés: Invoke-WebRequest -Uri http://localhost:8083/auth/v1/login -Method POST -Body '{ "username": "user@example.com", "password": "user" }' -ContentType "application/json" -Headers $csrfTokenHeader -WebSession $Session
     * //csrf előtti teszt//curl -X POST http://localhost:8083/auth/v1/login -H "Content-Type: application/json" -d "{\"username\": \"user@example.com\", \"password\": \"\$2a\$12\$ArwvDHRkuUGcWLuU6..Ire5g6tsuCTVC9ttzkurMwpPzW/HYnpX.G\"}"
     * */
    public Mono<String> login(@RequestBody AuthenticationRequest request) {
        log.debug("**********/auth/v1/login***********");
        log.debug("request: " + request.getUsername() + " - " + request.getPassword() + request);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        ).flatMap(authentication ->
                customerService.findCustomerWithRoles(request.getUsername())
                        .flatMap(customer -> jwtService.generateToken(authentication))
        );
    }

    @CrossOrigin(origins = "${angular.client.url}")
    @GetMapping("/csrf-token")
    public Mono<CsrfToken> getCsrfToken(ServerWebExchange exchange) {
        log.debug("**********/auth/csrf-token***********");
        return exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.<CsrfToken>empty())
                .doOnNext(token -> {
                    log.debug("Returning CSRF Token: {}", token.getToken());
                });
    }
}
