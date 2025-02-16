package com.onlinestore.auth2.service;

import com.onlinestore.auth2.domain.Customer;
import com.onlinestore.auth2.model.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDto> findCustomerWithRoles(String email);
    Mono<CustomerDto> saveCustomer(CustomerDto customerDto);
}
