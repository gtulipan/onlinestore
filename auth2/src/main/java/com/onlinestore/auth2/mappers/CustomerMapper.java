package com.onlinestore.auth2.mappers;

import com.onlinestore.auth2.domain.Customer;
import com.onlinestore.auth2.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDto toCustomerDto(Customer customer);
    Customer toCustomer(CustomerDto dto);
}
