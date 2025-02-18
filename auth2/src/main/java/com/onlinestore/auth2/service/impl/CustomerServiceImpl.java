package com.onlinestore.auth2.service.impl;

import com.onlinestore.auth2.domain.Customer;
import com.onlinestore.auth2.domain.RoleEntity;
import com.onlinestore.auth2.mappers.CustomerMapper;
import com.onlinestore.auth2.model.CustomerDto;
import com.onlinestore.auth2.repositories.CustomerRepository;
import com.onlinestore.auth2.repositories.RoleRepository;
import com.onlinestore.auth2.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private static final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Override
    public Mono<CustomerDto> findCustomerWithRoles(String email) {
        log.debug("CustomerServiceImpl, findCustomerWithRoles() email: {}", email);
        return customerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found for email: " + email)))
                .flatMap(customer ->
                        roleRepository.findByCustomerId(customer.getId())
                                .collectList()
                                .map(roles -> {
                                    customer.setRoles(new HashSet<>(roles));
                                    log.debug("Customer by mail is: "+customer);
                                    return customer;
                                })
                )
                .map(customerMapper::toCustomerDto);
    }

    @Override
    @Transactional
    public Mono<CustomerDto> saveCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
            customer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }
        customer.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
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
                                    if (role.getId() == null) {
                                        role.setId(UUID.randomUUID());
                                        role.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                                    }
                                    role.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
                                    return roleRepository.save(role);
                                })
                                .then(Mono.just(savedCustomer))
                )
                .map(customerMapper::toCustomerDto);
    }
}
