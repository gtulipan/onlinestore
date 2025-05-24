package com.onlinestore.auth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class ClientConfig {

    private final PasswordEncoder passwordEncoder;
    private final TokenSettings tokenSettings;

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("onlinestoreclientapp")
                .clientSecret(passwordEncoder.encode("9999"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8083/login/oauth2/code/custom")
                .scope("read")
                .scope("write")
                .tokenSettings(tokenSettings)
                .build();
        //TODO az értékeket át kell tenni környezeti változókba
        RegisteredClient productServiceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("product-service")
                .clientSecret(passwordEncoder.encode("secret1235"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("internal")
                .tokenSettings(tokenSettings)
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient, productServiceClient);
        //TODO a memory clien-t le kell cserélni majd JdbcRegisteredClientRepository -ra !
    }
}

