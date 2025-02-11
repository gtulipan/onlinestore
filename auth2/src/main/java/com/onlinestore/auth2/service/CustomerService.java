package com.onlinestore.auth2.service;

import com.onlinestore.auth2.domain.Customer;
import com.onlinestore.auth2.domain.RoleEntity;
import com.onlinestore.auth2.repositories.CustomerRepository;
import com.onlinestore.auth2.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public Mono<Customer> findCustomerWithRoles(String email) {
        return customerRepository.findByEmail(email)
                .flatMap(customer ->
                        roleRepository.findByCustomerId(customer.getId())
                                .collectList()
                                .map(roles -> {
                                    customer.setRoles(new HashSet<>(roles));
                                    return customer;
                                })
                );

    }

    @Transactional
    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepository.save(customer)
                .flatMap(savedCustomer ->
                        roleRepository.findByCustomerId(savedCustomer.getId())
                                .collectList()
                                .flatMap(existingRoles -> {
                                    Set<RoleEntity> rolesToDelete = new HashSet<>(existingRoles);
                                    rolesToDelete.removeAll(customer.getRoles());

                                    return roleRepository.deleteAll(rolesToDelete).thenReturn(savedCustomer);
                                })
                )
                .flatMap(savedCustomer ->
                        Flux.fromIterable(customer.getRoles())
                                .flatMap(role -> {
                                    role.setCustomerId(savedCustomer.getId());
                                    return roleRepository.save(role);
                                })
                                .then(Mono.just(savedCustomer))
                );
    }
}
