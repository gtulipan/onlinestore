package com.onlinestore.auth2.service;

import com.onlinestore.auth2.repositories.CustomerRepository;
import com.onlinestore.auth2.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.debug("ReactiveUserDetailsServiceImpl, findByUsername() username: {}", username);
        return customerRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found for email: " + username)))
                .flatMap(customer ->
                        roleRepository.findByCustomerId(customer.getId())
                                .collectList()
                                .map(roles -> {
                                    customer.setRoles(new HashSet<>(roles));
                                    return customer;
                                })
                )
                .map(customer -> new User(
                        customer.getEmail(),
                        customer.getPassword(),
                        customer.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                                .toList()
                ));
    }
}
