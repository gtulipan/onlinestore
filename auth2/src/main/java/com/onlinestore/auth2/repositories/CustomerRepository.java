package com.onlinestore.auth2.repositories;

import com.onlinestore.auth2.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, UUID> {
    Customer findByEmail(String email);
}
