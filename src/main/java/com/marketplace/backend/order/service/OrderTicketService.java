package com.marketplace.backend.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.backend.order.dto.OrderTicketResponseDTO;
import com.marketplace.backend.order.dto.UpdateOrderTicketDTO;
import com.marketplace.backend.order.entity.OrderTicketEntity;
import com.marketplace.backend.order.entity.ProductEntity;
import com.marketplace.backend.order.repository.OrderTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderTicketService {

//    private final OrderTicketRepository orderTicketRepository;
//    private final OrderService orderService;
//    private final ProductService productService;
//    private final ObjectMapper objectMapper;
//
//    @Transactional
//    public OrderTicketResponseDTO update(UUID uuid, UpdateOrderTicketDTO updateOrderTicketDTO) {
//        OrderTicketEntity existing = orderTicketRepository.findByUuidAndActiveTrue(uuid);
//        if (existing == null) {
//            throw new IllegalArgumentException("OrderTicket not found with UUID: " + uuid);
//        }
//
//        if (updateOrderTicketDTO.getOrderUuid() != null) {
//            existing.setOrderUuid(updateOrderTicketDTO.getOrderUuid());
//        }
//        if (updateOrderTicketDTO.getProducts() != null) {
//            List<ProductEntity> products = new ArrayList<>();
//            updateOrderTicketDTO.getProducts().forEach(productUuid -> {
//                ProductEntity product = productService.findByUuid(UUID.fromString(productUuid));
//                products.add(product);
//            });
//            orderService.update(products, updateOrderTicketDTO.getOrderUuid());
//        }
//        if (updateOrderTicketDTO.getObservations() != null && !updateOrderTicketDTO.getObservations().isEmpty()) {
//            orderService.updateObservationByTicket(updateOrderTicketDTO.getObservations(), updateOrderTicketDTO.getOrderUuid());
//        }
//
//        OrderTicketEntity updated = orderTicketRepository.save(existing);
//        return objectMapper.convertValue(updated, OrderTicketResponseDTO.class);
//    }
//
//    @Transactional
//    public void delete(UUID uuid) {
//        int rowsAffected = orderTicketRepository.deleteLogicallyByUuid(uuid);
//        if (rowsAffected == 0) {
//            throw new IllegalArgumentException("OrderTicket not found with UUID: " + uuid + " or already inactive");
//        }
//    }
//
//    public OrderTicketResponseDTO findByUuid(UUID uuid) {
//        OrderTicketEntity orderTicket = orderTicketRepository.findByUuidAndActiveTrue(uuid);
//        if (orderTicket == null) {
//            throw new IllegalArgumentException("OrderTicket not found with UUID: " + uuid);
//        }
//        return objectMapper.convertValue(orderTicket, OrderTicketResponseDTO.class);
//    }
//
//    public List<OrderTicketResponseDTO> findAll() {
//        List<OrderTicketEntity> orderTickets = orderTicketRepository.findAllByActiveTrue();
//        return orderTickets.stream()
//                .map(orderTicket -> objectMapper.convertValue(orderTicket, OrderTicketResponseDTO.class))
//                .collect(Collectors.toList());
//    }
}
