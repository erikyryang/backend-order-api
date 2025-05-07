package com.marketplace.backend.domain.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String uuid;

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String zipcode;

    @JsonProperty("isDefault")
    private boolean isDefault;
}
