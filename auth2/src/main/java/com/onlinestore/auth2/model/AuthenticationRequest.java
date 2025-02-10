package com.onlinestore.auth2.model;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
