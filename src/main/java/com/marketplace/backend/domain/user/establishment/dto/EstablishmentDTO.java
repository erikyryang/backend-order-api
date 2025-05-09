package com.marketplace.backend.domain.user.establishment.dto;

import com.marketplace.backend.domain.address.AddressDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EstablishmentDTO {
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private String phone;
    private List<AddressDTO> addresses;
}
