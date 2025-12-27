package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.mapper.ProductMapper;
import org.example.store.model.dto.ProductDTO;
import org.example.store.model.entity.Product;
import org.example.store.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public List<ProductDTO> findAll() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public List<ProductDTO> findAllByProductName(String productName) {
        return productRepo.findAllByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toDto);
    }

    public boolean existsById(Long id) {
        return productRepo.existsById(id);
    }

    public ProductDTO insert(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDto(productRepo.save(product));
    }

    public Optional<ProductDTO> update(Long id, ProductDTO productDTO) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setProductName(productDTO.getProductName());
                    product.setPrice(productDTO.getPrice());
                    product.setRating(productDTO.getRating());
                    product.setDescription(productDTO.getDescription());
                    return productMapper.toDto(productRepo.save(product));
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
}
