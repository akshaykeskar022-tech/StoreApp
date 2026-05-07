package com.example.store.service;

import com.example.store.dto.*;
import com.example.store.mapper.GenericModelMapper;
import com.example.store.model.Users;
import com.example.store.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    private final UsersRepository usersRepository;
    private final GenericModelMapper mapper;

    public UserService(UsersRepository usersRepository, GenericModelMapper genericModelMapper)
    {
        this.usersRepository = usersRepository;
        this.mapper = genericModelMapper;
    }

    public String adminSignUp (AdminSignUpDTO dto)
    {
        String adminSecretCode="OnlyAdmin4321";
        if(!dto.getAdminCode().equals(adminSecretCode))
            throw new RuntimeException("Invalid Admin code");

        Users user;
       if(usersRepository.existsByEmail(dto.getEmail()))
           throw new RuntimeException("Email already exists.");
       else
        user=mapper.convertToEntity(dto,Users.class);

       user.setRole("ADMIN");
       usersRepository.save(user);
       return "SignUp success";
    }

    public LoginResponseDTO login (LoginDTO dto)
    {
        Users user=usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(dto.getPassword().equals(user.getPassword()))
         return mapper.convertToDTO(user,LoginResponseDTO.class);
        else
            throw new RuntimeException("Invalid Password");
    }

    public String createCustomer(CustomerRequestDTO dto)
    {
        if(usersRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("Email id already exists.");

        Users user=mapper.convertToEntity(dto,Users.class);
        usersRepository.save(user);

        return "Customer created successfully.";
    }

    public List<CustomerResponseDTO> getAllUsers()
    {
        List<Users> usersList=usersRepository.findAll();
        List<CustomerResponseDTO> dtoList=new ArrayList<>();
        //     return studentList.stream().map(this::convertToDTO).toList();
        for(Users user:usersList)
          dtoList.add( mapper.convertToDTO(user,CustomerResponseDTO.class)) ;
        return dtoList;
    }

    public String removeUser(Integer userId)
    {
        usersRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        usersRepository.deleteById(userId);
        return "User removed successfully";
    }

}
