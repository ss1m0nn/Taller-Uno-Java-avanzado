package com.taller.ecommerce.dto;

public record ProductoRequestDTO(
        Long id,
        String nombre,
        Double precio,
        Integer stock,
        Long proveedorId
) {
}
