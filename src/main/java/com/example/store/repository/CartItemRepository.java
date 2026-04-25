package com.example.store.repository;

import com.example.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>
{
  Optional<CartItem> findByCartIdAndProductId(Integer cartId, Integer productId);

  @Modifying
  @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
  void deleteByCartId(@Param("cartId") Integer cartId);
}
