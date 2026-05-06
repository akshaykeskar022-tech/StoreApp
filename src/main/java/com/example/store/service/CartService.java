package com.example.store.service;

import com.example.store.dto.CartItemResponseDTO;
import com.example.store.dto.CartRequestDTO;
import com.example.store.dto.CartResponseDTO;
import com.example.store.dto.UpdateQuantityDTO;
import com.example.store.mapper.GenericModelMapper;
import com.example.store.model.Cart;
import com.example.store.model.CartItem;
import com.example.store.model.Products;
import com.example.store.model.Users;
import com.example.store.repository.CartItemRepository;
import com.example.store.repository.CartRepository;
import com.example.store.repository.ProductRepository;
import com.example.store.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final GenericModelMapper mapper;

    public CartService(CartRepository cartRepository, UsersRepository usersRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, GenericModelMapper mapper)
    {
        this.cartRepository = cartRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.mapper = mapper;
    }

    @Transactional
    public String addToCart(CartRequestDTO requestDTO)
    {
        //Get User
        Users user=usersRepository.findById(requestDTO.getUserId())
                .orElseThrow(()->new RuntimeException("User not found"));

        //Get Product
        Products product=productRepository.findById(requestDTO.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found"));

        //Get cart or create if not present
        Cart cart=cartRepository.findByUserId(user.getId())
                .orElseGet(()->
                { //create if not present
                  Cart newCart=new Cart();
                  newCart.setUser(user);
                  return cartRepository.save(newCart);
                });

        //Check if product already exists in cart
        CartItem cartItem=cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        //If already exists → update quantity
        if(cartItem!=null)
        {
           int newQuantity= cartItem.getQuantity()+requestDTO.getQuantity();
           cartItem.setQuantity(newQuantity);
           cartItemRepository.save(cartItem);
        }
         //If not exists add new CartItem
        else
        {
            cartItem =new CartItem();
            cartItem.setQuantity(requestDTO.getQuantity());
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItemRepository.save(cartItem);
        }

        return "Product added to cart successfully";
    }

    public CartResponseDTO getCartByUserId(Integer userId)
    {
        CartResponseDTO cartResponseDTO=new CartResponseDTO();

       Cart cart= cartRepository.findByUserId(userId).orElse(null);
       if(cart==null)
           return null;

       //Map CartItem to CartItemResponseDTO
        List<CartItemResponseDTO> itemResponseDTOList=new ArrayList<>();

        double totalAmount=0.0;
        for(CartItem item:cart.getCartItems() )
        {
            CartItemResponseDTO cartItemResponseDTO=new CartItemResponseDTO();
            cartItemResponseDTO.setId(item.getId());
            cartItemResponseDTO.setProductName(item.getProduct().getProductName());
            cartItemResponseDTO.setQuantity(item.getQuantity());
            cartItemResponseDTO.setMrp(item.getProduct().getMrp());
            double subTotal=item.getQuantity()*item.getProduct().getMrp();
            cartItemResponseDTO.setSubtotal(subTotal);

            itemResponseDTOList.add(cartItemResponseDTO);

            totalAmount+=subTotal;
        }
        cartResponseDTO.setItems(itemResponseDTOList);
        cartResponseDTO.setTotalAmount(totalAmount);

       return cartResponseDTO;
    }

    public String removeItem(Integer itemId)
    {
        CartItem cartItem= cartItemRepository.findById(itemId)
                .orElseThrow(()-> new RuntimeException("CartItem not found"));
        cartItemRepository.deleteById(itemId);
        return "Removed item successfully from cart";
    }

    public String updateQuantity(UpdateQuantityDTO dto)
    {
       CartItem cartItem= cartItemRepository.findById(dto.getId())
               .orElseThrow(()-> new RuntimeException("CartItem not found"));
       cartItem.setQuantity(dto.getQuantity());
        cartItemRepository.save(cartItem);
       return "Updated successfully";
    }
}
