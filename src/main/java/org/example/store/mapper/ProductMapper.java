package org.example.store.mapper;

import org.example.store.model.dto.ProductDTO;
import org.example.store.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryName", source = "category.categoryName")
    ProductDTO toDTO(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDTO productDTO);
}
