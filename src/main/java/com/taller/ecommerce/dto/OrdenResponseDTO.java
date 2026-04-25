package com.taller.ecommerce.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenResponseDTO(Long id, String usuarioNombre, String estado, Double total, LocalDateTime creadaEn, List<ItemOrdenResponseDTO> items) {

}
