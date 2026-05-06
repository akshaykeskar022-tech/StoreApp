package com.example.store.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityDTO
{
    private Integer id;
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
