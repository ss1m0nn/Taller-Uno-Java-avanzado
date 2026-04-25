package com.taller.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenResponseDTO(
        Long id,
        String estado,
        LocalDateTime fecha,
        Double total,
        List<ItemOrdenResponseDTO> items
) {
}
