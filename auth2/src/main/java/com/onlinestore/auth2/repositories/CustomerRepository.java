package com.onlinestore.auth2.repositories;

import com.onlinestore.auth2.domain.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerRepository extends R2dbcRepository<Customer, UUID> {
    Mono<Customer> findByEmail(String email);
}
