package com.example.store.controller;

import com.example.store.dto.CartRequestDTO;
import com.example.store.dto.CartResponseDTO;
import com.example.store.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class CartController
{
  private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public String addToCart(@Valid @RequestBody CartRequestDTO requestDTO)
    {
        return cartService.addToCart(requestDTO);
    }

    @GetMapping("/getCart/{userId}")
    public CartResponseDTO getCartByUserId(@PathVariable Integer userId)
    {
      return cartService.getCartByUserId(userId);
    }

}
