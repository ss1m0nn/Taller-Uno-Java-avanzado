package com.taller.ecommerce.controller;


import com.taller.ecommerce.dto.OrdenRequestDTO;
import com.taller.ecommerce.model.Orden;
import com.taller.ecommerce.repository.OrdenRepository;
import com.taller.ecommerce.service.OrdenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {
    private final OrdenService ordenService;
    private final OrdenRepository ordenRepository;
    public OrdenController(OrdenService ordenService, OrdenRepository ordenRepository) {
        this.ordenService = ordenService;
        this.ordenRepository = ordenRepository;
    }

    @GetMapping
    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }

    @PostMapping
    public void crearOrden(@RequestBody OrdenRequestDTO request) {
        ordenService.crearOrden(request);
    }
}
