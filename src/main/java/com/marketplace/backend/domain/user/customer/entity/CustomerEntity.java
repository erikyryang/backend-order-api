package com.marketplace.backend.domain.user.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.backend.domain.address.AddressEntity;
import com.marketplace.backend.domain.user.BaseUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_tbl")
public class CustomerEntity extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @JsonIgnore
    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private List<AddressEntity> addresses;

}
