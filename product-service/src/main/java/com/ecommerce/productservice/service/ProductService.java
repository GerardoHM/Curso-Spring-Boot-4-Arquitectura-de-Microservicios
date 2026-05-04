package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.dto.ProductRequestDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO reponseDTO);
    List<ProductResponseDTO> getAllsProducts();
    ProductResponseDTO getProductById(String id);
    ProductResponseDTO updateProductById(String id, ProductRequestDTO requestDTO);
    void deleteProductById(String id);
}
