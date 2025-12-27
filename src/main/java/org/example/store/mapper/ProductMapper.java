package org.example.store.mapper;

import org.example.store.model.dto.ProductDTO;
import org.example.store.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
}
