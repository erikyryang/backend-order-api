package com.marketplace.backend.domain.user;


import com.marketplace.backend.security.CustomUserDetailsService;
import com.marketplace.backend.security.Role;
import com.marketplace.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    @PostMapping("/restaurant/login")
    public ResponseEntity<?> restaurantLogin(@RequestBody AuthRequest authRequest,
                                             @RequestHeader("X-Tenant") String tenantId) throws Exception {
        UUID tenantUuid;
        try {
            tenantUuid = UUID.fromString(tenantId);
        } catch (IllegalArgumentException e) {
            throw new Exception("Cabeçalho X-Tenant inválido: deve ser um UUID válido");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Credenciais inválidas", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final BaseUserEntity user = (BaseUserEntity) userDetails;
        final Role role = user.getRole();

        if (role != Role.ESTABLISHMENT && role != Role.WAITER) {
            throw new Exception("Acesso negado: endpoint exclusivo para restaurantes e garçons");
        }

        final String jwt = jwtUtil.generateToken(authRequest.getEmail(), role, tenantUuid);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/customer/login")
    public ResponseEntity<?> customerLogin(@RequestBody AuthRequest authRequest,
                                           @RequestHeader("X-Tenant") String tenantId) throws Exception {
        UUID tenantUuid;
        try {
            tenantUuid = UUID.fromString(tenantId);
        } catch (IllegalArgumentException e) {
            throw new Exception("Cabeçalho X-Tenant inválido: deve ser um UUID válido");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Credenciais inválidas", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final BaseUserEntity user = (BaseUserEntity) userDetails;
        final Role role = user.getRole();

        if (role != Role.CUSTOMER) {
            throw new Exception("Acesso negado: endpoint exclusivo para clientes");
        }

        final String jwt = jwtUtil.generateToken(authRequest.getEmail(), role, tenantUuid);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}

class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}