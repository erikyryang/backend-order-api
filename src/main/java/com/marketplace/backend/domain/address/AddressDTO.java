package com.marketplace.backend.domain.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String zipcode;

    private boolean isDefault;
}
