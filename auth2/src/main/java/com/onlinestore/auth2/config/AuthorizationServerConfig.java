package com.onlinestore.auth2.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
//@EnableWebFluxSecurity
public class AuthorizationServerConfig {

    private final UserDetailsService userDetailsService;
    private static final String ROLES_CLAIM = "roles";

    @Value("${spring.security.keyFile}")
    private String keyFile;

    @Value("${spring.security.password}")
    private String password;

    @Value("${spring.security.alias}")
    private String alias;

    @Value("${spring.security.providerUrl}")
    private String providerUrl;

    @Value("${spring.security.token-timeout}")
    private String tokenTimeout;

    /**
     * <p>Amikor az authorization szerver konfigurációjában beállítjuk az oauth részére a HttpSecurity objektumot,
     * akkor az abban beállított http kérések lesznek beállítva.</p>
     */
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
////        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http); //deprecated
//        http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
//        return http.userDetailsService(userDetailsService).formLogin(Customizer.withDefaults()).build();
//    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(new AntPathRequestMatcher("/oauth2/**"))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * <p>Ez a bean fogja elvégezni a token kódolását.</p>
     */
    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * <p>Ehhez szükséges egy jwk source, amiből a publikus és a privát kulcsok kiolvashatóak.</p>
     */
    @Bean
    JWKSource<SecurityContext> jwkSource()
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        JWKSet jwkSet = buildJWKSet();
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * <p>A jwk selector fogja visszaadni a jwk set-ből a jwk source-t. Azaz szükséges egy JWKSet konfiguráció is</p>
     */
    private JWKSet buildJWKSet() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        try (InputStream fis = this.getClass().getClassLoader().getResourceAsStream(keyFile);) {
            keyStore.load(fis, alias.toCharArray());
            return JWKSet.load(keyStore, name -> password.toCharArray());
        }
    }

    /**
     * <p>Provider-nek kell konfigurálni az authorization szerverünket. Ehhez egy AuthorizationSer-verSettings beant kell létrehozni és beállítani az
     * application.properties-ben lévő URL segítségével.</p>
     */
    @Bean
    AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder().issuer(providerUrl).build();
    }

    /**
     * <p>Az authorization konfigurációban a RegisteredClientRepository bean létrehozásához megadtunk egy tokenSettings() metódus hívást.
     * A metódus egy TokenSettings bean-t hoz létre</p>
     * */
    @Bean
    TokenSettings tokenSettings() {
        return TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(Long.parseLong(tokenTimeout))).build();

    }

    /**
     * <p>mikortól az OAUTH önálló project lett, akkor az automatikus role beállítás kikerült a projekt-ből.
     * Ezért külön bean-t kell létrehoznunk az authorization server config -ban,
     * kiszedni a context által tartalmazott principal-ból és hozzáadni a context által tartalmazott claims-hez</p>
     * */
    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                Authentication principal = context.getPrincipal();
                Set<String> authorities = principal.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim(ROLES_CLAIM, authorities);
            }
        };
    }

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange(authorize ->
//                authorize
//                        .anyExchange().authenticated()
//        ).oauth2ResourceServer(oauth2 ->
//                oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        return http.build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
//    }
//
//    // Add your public key here
//    private RSAPublicKey publicKey() {
//        // Load your public key
//    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return ReactiveJwtDecoders.fromIssuerLocation("https://my-auth-server.com");
//    }
}
