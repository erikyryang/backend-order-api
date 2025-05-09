package com.marketplace.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    WAITER("WAITER"),
    CUSTOMER("CUSTOMER"),
    ESTABLISHMENT("ESTABLISHMENT");

    private final String label;

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}