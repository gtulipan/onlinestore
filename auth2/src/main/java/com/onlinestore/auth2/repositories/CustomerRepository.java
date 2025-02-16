package com.onlinestore.auth2.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.onlinestore.auth2.domain.Customer;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, UUID> {
    @Query("SELECT customer.id, customer.name, customer.email, customer.password, customer.phone_number, customer.address, customer.status, customer.created_date, customer.last_modified_date, customer.version FROM customer WHERE customer.email = :email")
    Mono<Customer> findByEmail(@Param("email") String email);
}
