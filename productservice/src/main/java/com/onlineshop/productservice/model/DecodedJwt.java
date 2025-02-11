package com.onlineshop.productservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class DecodedJwt {
    private String subject;
    private String issuer;
    private long expiration;
    private Map<String, Object> claims;
}
