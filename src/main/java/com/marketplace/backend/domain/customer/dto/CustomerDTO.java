package com.marketplace.backend.domain.customer.dto;

import com.marketplace.backend.domain.address.AddressDTO;
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

    private String password;

    private String phone;

    private List<AddressDTO> addresses;
}
