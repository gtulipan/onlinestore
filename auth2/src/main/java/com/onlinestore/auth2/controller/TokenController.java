package com.onlinestore.auth2.controller;

import com.onlinestore.auth2.model.AuthenticationRequest;
import com.onlinestore.auth2.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final JwtService jwtService;

    public TokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

//    @PostMapping
//    public String generateToken(@RequestBody AuthenticationRequest request) {
//        // Implementáld a token generálását
//        Authentication authentication = null;// authenticate the user
//        return jwtService.generateToken(authentication);
//    }
}
