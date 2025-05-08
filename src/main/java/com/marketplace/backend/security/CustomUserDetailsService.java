package com.marketplace.backend.security;

import com.marketplace.backend.domain.user.BaseUserEntity;
import com.marketplace.backend.domain.user.customer.repository.CustomerRepository;
import com.marketplace.backend.domain.user.establishment.repository.EstablishmentRepository;
import com.marketplace.backend.domain.user.waiter.WaiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final WaiterRepository waiterRepository;

    private final CustomerRepository customerRepository;

    private final EstablishmentRepository establishmentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UUID tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            throw new UsernameNotFoundException("Tenant ID não especificado");
        }

        return waiterRepository.findByEmailAndTenantIdAndActiveTrue(email, tenantId)
                .map(BaseUserEntity.class::cast)
                .orElseGet(() -> customerRepository.findByEmailAndTenantIdAndActiveTrue(email, tenantId)
                        .map(BaseUserEntity.class::cast)
                        .orElseGet(() -> establishmentRepository.findByEmailAndTenantIdAndActiveTrue(email, tenantId)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email + " no tenant " + tenantId))));
    }
}
