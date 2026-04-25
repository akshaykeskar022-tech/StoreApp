package com.example.store.service;

import com.example.store.dto.CartItemResponseDTO;
import com.example.store.dto.OrderItemResponseDTO;
import com.example.store.dto.OrderRequestDTO;
import com.example.store.dto.OrderResponseDTO;
import com.example.store.mapper.GenericModelMapper;
import com.example.store.model.*;
import com.example.store.repository.*;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.RasterFormatException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService
{
    public final OrdersRepository ordersRepository;
    public final OrderItemRepository orderItemRepository;
    public final UsersRepository usersRepository;
    public final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    public final GenericModelMapper mapper;

    public OrderService(GenericModelMapper mapper, OrdersRepository ordersRepository, OrderItemRepository orderItemRepository, UsersRepository usersRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.mapper = mapper;
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.usersRepository = usersRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public String placeOrder( OrderRequestDTO requestDTO)
    {
        //Get User
        Users user=usersRepository.findById(requestDTO.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));

        //Get Cart
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        //Check CartItem
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty())
            throw new RuntimeException("Cart is empty");

        //Create Order
        Orders order = new Orders();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

         //Convert CartItems -> OrderItems
       List<OrderItem> orderItemList = new ArrayList<>();
       double total=0.0;

       for(CartItem cartItem:cart.getCartItems())
       {
           OrderItem orderItem = new OrderItem();
           orderItem.setOrder(order);
           orderItem.setProductName(cartItem.getProduct().getProductName());
           orderItem.setMrp(cartItem.getProduct().getMrp());
           orderItem.setQuantity(cartItem.getQuantity());

           orderItemList.add(orderItem);

           total+=cartItem.getQuantity()*cartItem.getProduct().getMrp();
       }

       order.setOrderItems(orderItemList);
       order.setTotalAmount(total);
       //Save Order and CascadeType.ALL save OrderItem
       ordersRepository.save(order);

       //Clear Cart
        cartItemRepository.deleteByCartId(cart.getId());
        cartRepository.deleteByCartId(cart.getId());
       return "Order created successfully";
    }

    public List<OrderResponseDTO> getOrderHistory(Integer userId)
    {
        //Get User
        Users user=usersRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        //Get Order
       List<Orders> orderList=ordersRepository.findByUserId(userId);
       return mapOrderToResponse(orderList);
    }

    public List<OrderResponseDTO> getAllOrders()
    {
       // return mapOrderToResponse(ordersRepository.findAll());
        List<Orders> orderList=ordersRepository.findAll();
        return mapOrderToResponse(orderList);
    }

    public List<OrderResponseDTO> mapOrderToResponse(List<Orders> orderList)
    {
        if(orderList==null || orderList.isEmpty())
            return Collections.emptyList();

        List<OrderResponseDTO> responseDTOList=new ArrayList<>();

        for(Orders order:orderList)
        {
            OrderResponseDTO responseDTO=new OrderResponseDTO();
            responseDTO.setOrderId(order.getId());
            responseDTO.setCreatedAt(order.getCreatedAt());
            responseDTO.setTotalAmount(order.getTotalAmount());

            List<OrderItemResponseDTO> itemResponseDTOList = new ArrayList<>();
            for(OrderItem orderItem: order.getOrderItems())
            {
                OrderItemResponseDTO itemResponseDTO=new OrderItemResponseDTO();
                itemResponseDTO.setProductName(orderItem.getProductName());
                itemResponseDTO.setQuantity(orderItem.getQuantity());
                itemResponseDTO.setMrp(orderItem.getMrp());
                double subTotal=orderItem.getQuantity()*orderItem.getMrp();
                itemResponseDTO.setSubtotal(subTotal);

                itemResponseDTOList.add(itemResponseDTO);
            }
            responseDTO.setOrderItemResponseDTOList(itemResponseDTOList);
            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }
}
