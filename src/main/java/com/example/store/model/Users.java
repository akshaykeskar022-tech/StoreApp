package com.example.store.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter(lombok.AccessLevel.NONE)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Orders> ordersList;

}
