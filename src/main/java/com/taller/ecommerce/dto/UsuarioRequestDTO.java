package com.taller.ecommerce.dto;

import com.taller.ecommerce.model.Usuario;
import org.springframework.web.bind.annotation.RequestBody;

public record UsuarioRequestDTO(String nombre, String correo, String contraseña, String rol) {

}
