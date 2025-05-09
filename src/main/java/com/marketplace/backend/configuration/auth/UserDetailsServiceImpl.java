package com.marketplace.backend.configuration.auth;


import com.marketplace.backend.domain.user.AbstractUserEntity;
import com.marketplace.backend.domain.user.customer.repository.CustomerRepository;
import com.marketplace.backend.domain.user.establishment.repository.EstablishmentRepository;
import com.marketplace.backend.domain.user.waiter.WaiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final WaiterRepository waiterRepository;

    private final CustomerRepository customerRepository;

    private final EstablishmentRepository establishmentRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        AbstractUserEntity user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new UserDetailsImpl(user);
    }

    private AbstractUserEntity findUserByEmail(String email) {
        return waiterRepository.findByEmailAndActiveTrue(email)
                .map(AbstractUserEntity.class::cast)
                .orElseGet(() -> customerRepository.findByEmailAndActiveTrue(email)
                        .map(AbstractUserEntity.class::cast)
                        .orElseGet(() -> establishmentRepository.findByEmailAndActiveTrue(email)
                                .orElse(null)));
    }
}