package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO
{
    private Integer id;
    private List<CartItemResponseDTO> items;
    private double totalAmount;
}