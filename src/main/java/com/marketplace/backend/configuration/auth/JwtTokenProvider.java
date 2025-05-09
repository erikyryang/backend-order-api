package com.marketplace.backend.configuration.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpiration;

    private byte[] secretKeyBytes;

    @PostConstruct
    public void init() {
        if ("default-secret-key".equals(jwtSecret)) {
            throw new IllegalStateException("A chave secreta JWT deve ser configurada em application.yml");
        }
        if (jwtExpiration <= 0) {
            throw new IllegalStateException("A expiração do JWT deve ser um valor positivo em milissegundos");
        }
        // Codifica a chave secreta em Base64 para garantir comprimento adequado
        this.secretKeyBytes = Base64.getEncoder().encode(jwtSecret.getBytes(StandardCharsets.UTF_8));
        log.debug("Chave secreta JWT inicializada com sucesso");
    }

    public String generateToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl)) {
            log.error("Esperado UserDetailsImpl, recebido: {}", principal.getClass().getName());
            throw new IllegalStateException("O principal deve ser UserDetailsImpl");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        log.debug("Gerando JWT para email: {}", userDetails.getUsername());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), Jwts.SIG.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKeyBytes))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKeyBytes))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }
}