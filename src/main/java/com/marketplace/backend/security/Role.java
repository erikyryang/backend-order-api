package com.marketplace.backend.security;

public enum Role {
    WAITER("ROLE_WAITER"),
    CUSTOMER("ROLE_CUSTOMER"),
    ESTABLISHMENT("ROLE_ESTABLISHMENT");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
