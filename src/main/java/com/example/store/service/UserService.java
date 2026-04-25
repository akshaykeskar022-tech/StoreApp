package com.example.store.service;

import com.example.store.dto.AdminSignUpDTO;
import com.example.store.dto.CustomerRequestDTO;
import com.example.store.dto.LoginDTO;
import com.example.store.mapper.GenericModelMapper;
import com.example.store.model.Users;
import com.example.store.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.reader.ReaderException;

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
        Users user;
       if(usersRepository.existsByEmail(dto.getEmail()))
           throw new RuntimeException("Email already exists.");
       else
        user=mapper.convertToEntity(dto,Users.class);

       user.setRole("ADMIN");
       usersRepository.save(user);
       return "SignUp success;";
    }

    public String login (LoginDTO dto)
    {
        Users user=usersRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));

        if(dto.getPassword().equals(user.getPassword()))
            return "Login successfully";
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
}
