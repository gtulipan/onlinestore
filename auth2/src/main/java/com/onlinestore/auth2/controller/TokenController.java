package com.onlinestore.auth2.controller;

import com.onlinestore.auth2.model.AuthenticationRequest;
import com.onlinestore.auth2.service.CustomerService;
import com.onlinestore.auth2.service.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@OpenAPIDefinition(info = @Info(title = "Auth2 Service", version = "v1"))
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtService jwtService;
    private final CustomerService customerService;
    private final ReactiveAuthenticationManager authenticationManager;

    @Operation(summary = "POST request for token", description = "Generate new token based on user details.")
    @PostMapping
    public Mono<String> generateToken(@RequestBody AuthenticationRequest request) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        ).flatMap(authentication ->
                customerService.findCustomerWithRoles(request.getUsername())
                        .flatMap(customer -> jwtService.generateToken(authentication))
        );
    }
}
