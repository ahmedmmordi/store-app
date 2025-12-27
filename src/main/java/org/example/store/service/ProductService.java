package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.mapper.ProductMapper;
import org.example.store.model.dto.ProductDTO;
import org.example.store.model.entity.Category;
import org.example.store.model.entity.Product;
import org.example.store.repository.CategoryRepo;
import org.example.store.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final CategoryRepo categoryRepo;

    public List<ProductDTO> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public List<ProductDTO> findAllByProductName(String productName) {
        return productRepo.findAllByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toDTO);
    }

    private boolean existsById(Long id) {
        return productRepo.existsById(id);
    }

    public ProductDTO insert(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Category category = getCategoryByName(productDTO.getCategoryName());
        product.setCategory(category);
        return productMapper.toDTO(productRepo.save(product));
    }

    public Optional<ProductDTO> update(Long id, ProductDTO productDTO) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setProductName(productDTO.getProductName());
                    product.setPrice(productDTO.getPrice());
                    product.setRating(productDTO.getRating());
                    product.setDescription(productDTO.getDescription());

                    if (productDTO.getCategoryName() != null) {
                        Category category = getCategoryByName(productDTO.getCategoryName());
                        product.setCategory(category);
                    }

                    return productMapper.toDTO(productRepo.save(product));
                });
    }

    public boolean delete(Long id) {
        boolean exists = this.existsById(id);
        if (!exists) {
            return false;
        }
        productRepo.deleteById(id);
        return true;
    }

    private Category getCategoryByName(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category '" + categoryName + "' Not Found."));
    }
}
