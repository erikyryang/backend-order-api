package com.marketplace.backend.util;

import com.marketplace.backend.security.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "teste"; //TODO: trocar dps e colocar nas propriedades
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public String generateToken(String email, Role role, UUID tenantId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .claim("tenantId", tenantId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Role extractRole(String token) {
        String roleName = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("role", String.class);
        return Role.valueOf(roleName);
    }

    public UUID extractTenantId(String token) {
        String tenantIdStr = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("tenantId", String.class);
        return UUID.fromString(tenantIdStr);
    }

    public boolean validateToken(String token, UUID tenantId) {
        try {
            UUID tokenTenantId = extractTenantId(token);
            return tenantId.equals(tokenTenantId) && Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
