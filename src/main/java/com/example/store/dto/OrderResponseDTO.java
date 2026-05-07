package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO
{
    private Integer orderId;

    private String userName;

    private double totalAmount;

    private LocalDateTime createdAt;

    private List<OrderItemResponseDTO> orderItemResponseDTOList;
}
