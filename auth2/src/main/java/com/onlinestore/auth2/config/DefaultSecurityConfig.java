package com.onlinestore.auth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DefaultSecurityConfig {

    /**
     * <p>Korábban a HttpSecurity-ban http kéréseket állítottunk be.
     * E helyett most hozzunk létre egy  osztályt az alapértelmezett biztonsági beállításokhoz.
     * Legyen ez a DefaultSecurityConfig. Ebben az osztályban  az lesz beállítva,
     * hogy minden http request-nek engedélyezettnek kell lennie
     * és ez az engedélyezés a form login segítségével lesz elvégezve, mely a default Cus-tomizer-t használja.
     * Ezzel a web alkalmazásunknak alapértelmezett security-t konfigurálunk be.
     * Ez a metódus az authorization szerverünk részére állítja be az alapértelmezett security-t. </p>
     * */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * <p>A jelszóhoz szükségünk lesz egy encoder-re. </p>
     * */
    @Bean
    PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
