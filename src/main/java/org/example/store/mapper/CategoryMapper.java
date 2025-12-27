package org.example.store.mapper;

import org.example.store.model.dto.CategoryDTO;
import org.example.store.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {
    @Mapping(target = "products", source = "products")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "products", source = "products")
    Category toEntity(CategoryDTO dto);
}
