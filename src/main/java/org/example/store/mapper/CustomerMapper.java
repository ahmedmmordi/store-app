package org.example.store.mapper;

import org.example.store.model.dto.CustomerDTO;
import org.example.store.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
