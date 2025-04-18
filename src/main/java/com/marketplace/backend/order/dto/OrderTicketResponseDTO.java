package com.marketplace.backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketResponseDTO {
    private UUID uuid;
    private UUID orderUuid;
    private List<String> productUuids;
    private String status;
    private LocalDateTime createdAt;
    private boolean active;
}