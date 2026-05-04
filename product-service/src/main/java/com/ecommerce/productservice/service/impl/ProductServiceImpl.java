package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    /**
     *
     */
    public static final String PRODUCT_NOT_FOUND_BY_ID = "Product no encontrado por id: ";
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(final ProductRequestDTO requestDTO) {
        final Product product = mapper.toProduct(requestDTO);
        Product savedProduct = repository.save(product);
        log.info("Product {} guardado.", savedProduct.getName());

        return mapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllsProducts() {
        return repository.findAll().stream()
                .map(mapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(final String id) {
        Optional<Product> byId = repository.findById(id);
        final Product product = byId
                .orElseThrow(
                        () -> new ResourceNotFoundException("Producto", "id", id)
                );
        
        return mapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProductById(String id, ProductRequestDTO requestDTO) {
        final Optional<Product> byId = repository.findById(id);
        final Product product = byId
                .orElseThrow(
                        () -> new ResourceNotFoundException("Producto", "id", id)
                );
        mapper.updateProductFromRequestDTO(requestDTO, product);
        Product updatedProduct = repository.save(product);
        log.info("Product {} actualizado.", updatedProduct.getName());

        return mapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(String id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", "id", id);
        }

        repository.deleteById(id);
        log.info("Producto con el id:{} fue eliminado.", id);
    }
}
