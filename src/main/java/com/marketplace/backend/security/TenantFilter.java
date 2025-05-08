package com.marketplace.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tenantIdStr = request.getHeader("X-Tenant");
        UUID tenantId = null;

        if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
            try {
                tenantId = UUID.fromString(tenantIdStr);
                TenantContext.setTenantId(tenantId);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cabeçalho X-Tenant inválido: deve ser um UUID válido");
                return;
            }
        } else if (!request.getRequestURI().startsWith("/api/auth/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cabeçalho X-Tenant é obrigatório");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
