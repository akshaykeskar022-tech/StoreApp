package com.example.store.repository;

import com.example.store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>
{
    Optional<Cart> findByUserId(Integer userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.id = :cartId")
    void deleteByCartId(@Param("cartId") Integer cartId);
}
