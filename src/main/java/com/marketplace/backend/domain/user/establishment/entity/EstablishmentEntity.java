package com.marketplace.backend.domain.user.establishment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.backend.domain.address.AddressEntity;
import com.marketplace.backend.domain.user.BaseUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "establishment_tbl")
public class EstablishmentEntity extends BaseUserEntity {

    @JsonIgnore
    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "establishment_id")
    private List<AddressEntity> addresses;

}
