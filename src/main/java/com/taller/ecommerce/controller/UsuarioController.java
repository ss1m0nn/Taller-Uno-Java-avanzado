package com.taller.ecommerce.controller;

import com.taller.ecommerce.dto.UsuarioRequestDTO;
import com.taller.ecommerce.model.Usuario;
import com.taller.ecommerce.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario registrar(@RequestBody UsuarioRequestDTO request) {

        return usuarioService.registrar(
                request.nombre(),
                request.correo(),
                request.contraseña(),
                request.rol()
        );
    }
}
