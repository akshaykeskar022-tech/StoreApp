package com.example.store.controller;

import com.example.store.dto.ProductRequestDTO;
import com.example.store.dto.ProductResponseDTO;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping("/admin/addProduct")
    public String createProduct(@Valid @RequestBody ProductRequestDTO requestDTO)
    {
        return productService.createProduct(requestDTO);
    }

    @GetMapping("/getAllProduct")
    public List<ProductResponseDTO> getAllProducts()
    {
       return productService.getAllProducts();
    }



}
