package com.taller.ecommerce.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String mensaje, LocalDateTime timestamp) {
}
