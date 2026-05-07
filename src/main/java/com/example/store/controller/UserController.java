package com.example.store.controller;

import com.example.store.dto.*;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/signUp")
    public String adminSignUp(@Valid @RequestBody AdminSignUpDTO dto)
    {
     return  userService.adminSignUp(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO dto)
    {
        return userService.login(dto);
    }

   @PostMapping("/admin/createCustomer")
   public String createCustomer(@Valid @RequestBody CustomerRequestDTO dto)
   {
       return userService.createCustomer(dto);
   }

   @GetMapping("/admin/getUsers")
   public List<CustomerResponseDTO> getAllUsers()
   {
       return userService.getAllUsers();
   }

   @DeleteMapping("/admin/removeUser/{userId}")
   public String removeUser(@PathVariable Integer userId)
   {
      return userService.removeUser(userId);
   }

}
