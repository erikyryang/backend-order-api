package com.marketplace.backend.domain.user.waiter;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WaiterDTO {
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String employeeId;
}
