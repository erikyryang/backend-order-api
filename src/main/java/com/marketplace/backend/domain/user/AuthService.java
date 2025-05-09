package com.marketplace.backend.domain.user;

import com.marketplace.backend.domain.user.customer.CustomerService;
import com.marketplace.backend.domain.user.customer.dto.CustomerDTO;
import com.marketplace.backend.domain.user.customer.dto.LoginDTO;
import com.marketplace.backend.domain.user.establishment.EstablishmentService;
import com.marketplace.backend.domain.user.establishment.dto.EstablishmentDTO;
import com.marketplace.backend.domain.user.waiter.WaiterDTO;
import com.marketplace.backend.domain.user.waiter.WaiterService;
import com.marketplace.backend.configuration.auth.JwtResponse;
import com.marketplace.backend.configuration.auth.JwtTokenProvider;
import com.marketplace.backend.configuration.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final WaiterService waiterService;

    private final CustomerService customerService;

    private final EstablishmentService establishmentService;

    public JwtResponse authenticateCustomer(LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().stream()
                    .noneMatch(auth -> auth.getAuthority().equals(RoleEnum.CUSTOMER.getRoleName()))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }

            String jwt = jwtTokenProvider.generateToken(authentication);
            return new JwtResponse(jwt);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    public JwtResponse authenticateWaiter(LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().stream()
                    .noneMatch(auth -> auth.getAuthority().equals(RoleEnum.WAITER.getRoleName()))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }

            String jwt = jwtTokenProvider.generateToken(authentication);
            return new JwtResponse(jwt);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    public JwtResponse authenticateEstablishment(LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.getAuthorities().stream()
                    .noneMatch(auth -> auth.getAuthority().equals("ROLE_ESTABLISHMENT"))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
            }

            String jwt = jwtTokenProvider.generateToken(authentication);
            return new JwtResponse(jwt);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
       return customerService.create(customerDTO);
    }

    public WaiterDTO registerWaiter(WaiterDTO waiterDTO) {
        return waiterService.create(waiterDTO);
    }

    public EstablishmentDTO registerEstablishment(EstablishmentDTO establishmentDTO) {
        return establishmentService.create(establishmentDTO);
    }
}