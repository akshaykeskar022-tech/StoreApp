package com.example.store.controller;

import com.example.store.dto.AdminSignUpDTO;
import com.example.store.dto.CustomerRequestDTO;
import com.example.store.dto.LoginDTO;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@Valid @RequestBody LoginDTO dto)
    {
        return userService.login(dto);
    }

   @PostMapping("/admin/createCustomer")
   public String createCustomer(@Valid @RequestBody CustomerRequestDTO dto)
   {
       return userService.createCustomer(dto);
   }

}
