package com.marketplace.backend.domain.user.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.backend.domain.address.AddressDTO;
import com.marketplace.backend.domain.user.RoleEnum;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDTO {

    @Nullable
    private String uuid;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private String phone;

    private List<AddressDTO> addresses;
}
