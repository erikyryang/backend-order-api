package com.marketplace.backend.domain.user.establishment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.backend.domain.address.AddressEntity;
import com.marketplace.backend.domain.user.AbstractUserEntity;
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
@Table(name = "establishment_tbl")
public class EstablishmentEntity extends AbstractUserEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "establishment_id")
    private List<AddressEntity> addresses;

}
