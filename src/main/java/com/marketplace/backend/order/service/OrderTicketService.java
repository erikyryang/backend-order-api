package com.marketplace.backend.order.service;

import com.marketplace.backend.order.repository.OrderTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderTicketService {

    private final OrderTicketRepository orderTicketRepository;
}
