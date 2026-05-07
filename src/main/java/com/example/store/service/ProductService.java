package com.example.store.service;

import com.example.store.dto.ProductRequestDTO;
import com.example.store.dto.ProductResponseDTO;
import com.example.store.dto.UpdateProductDTO;
import com.example.store.mapper.GenericModelMapper;
import com.example.store.model.Products;
import com.example.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService
{
  private final ProductRepository productRepository;
  private final GenericModelMapper mapper;

    public ProductService(ProductRepository productRepository, GenericModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public String createProduct(ProductRequestDTO requestDTO)
    {
       if (productRepository.existsByProductName(requestDTO.getProductName()))
        throw new RuntimeException("Product name already exists.");

       Products product=mapper.convertToEntity(requestDTO,Products.class);
       productRepository.save(product);
       return "Product added successfully.";
    }

    public List<ProductResponseDTO> getAllProducts()
    {
        List<Products> productList=productRepository.findAll();

        List<ProductResponseDTO> responseDTOList =new ArrayList<>();
        for(Products p:productList)
        {
            ProductResponseDTO responseDTO=mapper.convertToDTO(p,ProductResponseDTO.class);
            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }

    public String removeProduct(Integer productId)
    {
        Products product=productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product Not found"));
        productRepository.deleteById(productId);
        return "Product removed successfully";
    }

    public String updateProduct(UpdateProductDTO requestDTO)
    {
       Products product= productRepository.findById(requestDTO.getId())
                .orElseThrow(()->new RuntimeException("Product not found"));
       product.setProductName(requestDTO.getProductName());
       product.setMrp(requestDTO.getMrp());
       productRepository.save(product);
       return "Product updated successfully";
    }
}
