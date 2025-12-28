package org.example.store.mapper;

import org.example.store.model.dto.OrderDTO;
import org.example.store.model.entity.Customer;
import org.example.store.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "customer.id", target = "customerId")
    OrderDTO toDTO(Order order);

    @Mapping(source = "customerId", target = "customer", qualifiedByName = "mapCustomer")
    Order toEntity(OrderDTO orderDTO);

    @Named("mapCustomer")
    default Customer mapCustomer(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
