package com.taller.ecommerce.dto;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        Double precio,
        Integer stock,
        Boolean activo
) {
}
