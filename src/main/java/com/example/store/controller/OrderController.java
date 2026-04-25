package com.example.store.controller;

import com.example.store.dto.OrderRequestDTO;
import com.example.store.dto.OrderResponseDTO;
import com.example.store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class OrderController
{
    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody OrderRequestDTO requestDTO)
    {
       return orderService.placeOrder(requestDTO);
    }

    @GetMapping("/getOrderHistory/{userId}")
    public List<OrderResponseDTO> getOrderHistory(@PathVariable Integer userId)
    {
        return orderService.getOrderHistory(userId);
    }

    @GetMapping("/admin/getAllOrders")
    public List<OrderResponseDTO> getAllOrders()
    {
        return orderService.getAllOrders();
    }
}
