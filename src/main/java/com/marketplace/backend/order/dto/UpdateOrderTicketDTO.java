package com.marketplace.backend.order.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderTicketDTO {
    private UUID orderUuid;
    private List<String> products;
    private String observations;
    private String status;
}
