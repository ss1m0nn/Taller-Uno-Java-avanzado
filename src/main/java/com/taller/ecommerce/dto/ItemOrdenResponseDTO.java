package com.taller.ecommerce.dto;

public record ItemOrdenResponseDTO(
        Long productoId,
        String nombre,
        Integer cantidad,
        Double precioUnitario,
        Double subtotal) {

}
