package com.example.store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Setter(lombok.AccessLevel.NONE)
   private Integer id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private Users user;

   private double totalAmount;

   private LocalDateTime createdAt;

   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
   private List<OrderItem> orderItems;



}
