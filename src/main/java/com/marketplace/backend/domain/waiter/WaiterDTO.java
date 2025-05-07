package com.marketplace.backend.domain.waiter;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WaiterDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private String email;
    private String phone;
    private String employeeId;
}
