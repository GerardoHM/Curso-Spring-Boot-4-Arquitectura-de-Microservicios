package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(){
        return this.productService.getAllsProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponseDTO createProduct(@RequestBody @Valid ProductRequestDTO  productRequestDTO){
        return this.productService.createProduct(productRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getProductById(@PathVariable String id){
        return this.productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable String id){
        this.productService.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProductById(@PathVariable String id, @RequestBody @Valid ProductRequestDTO  productRequestDTO){
        return this.productService.updateProductById(id, productRequestDTO);
    }

    @GetMapping("/test-fail")
    public ProductResponseDTO testFail(){
        throw new RuntimeException("¡Boom! La base de datos explotó (simulacro)");
    }
}
