package com.marketplace.backend.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;

    private String observations;


}
