package com.taller.ecommerce.security;

import com.taller.ecommerce.service.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final AuditoriaService auditoriaService;

    public CustomAccessDeniedHandler(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

        auditoriaService.registrar(
                "ACCESS_DENIED",
                "SECURITY",
                null,
                usuario,
                "Intento de acceso no autorizado a " + request.getRequestURI()
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"mensaje\": \"Acceso denegado\", \"timestamp\": \"" + LocalDateTime.now() + "\"}"
        );
    }
}
