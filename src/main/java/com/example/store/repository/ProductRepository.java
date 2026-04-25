package com.example.store.repository;

import com.example.store.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer>
{
    Optional<Products> findByProductName(String productName);

    boolean existsByProductName(String productName);
}
