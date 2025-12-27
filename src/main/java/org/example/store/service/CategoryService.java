package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.mapper.CategoryMapper;
import org.example.store.model.dto.CategoryDTO;
import org.example.store.model.entity.Category;
import org.example.store.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> findAll() {
        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public List<CategoryDTO> findAllByCategoryName(String categoryName) {
        return categoryRepo.findAllByCategoryNameContainingIgnoreCase(categoryName)
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepo.findById(id)
                .map(categoryMapper::toDTO);
    }

    private boolean existsById(Long id) {
        return categoryRepo.existsById(id);
    }

    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryMapper.toDTO(categoryRepo.save(category));
    }

    public Optional<CategoryDTO> update(Long id, CategoryDTO categoryDTO) {
        return categoryRepo.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(categoryDTO.getCategoryName());
                    existingCategory.setDescription(categoryDTO.getDescription());
                    return categoryMapper.toDTO(categoryRepo.save(existingCategory));
                });
    }

    public boolean delete(Long id) {
        boolean exists = this.existsById(id);
        if (!exists) {
            return false;
        }
        categoryRepo.deleteById(id);
        return true;
    }
}
