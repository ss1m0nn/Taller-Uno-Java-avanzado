package com.taller.ecommerce.service;

import com.taller.ecommerce.model.ItemOrden;
import com.taller.ecommerce.model.Rol;
import com.taller.ecommerce.model.Usuario;
import com.taller.ecommerce.repository.RolRepository;
import com.taller.ecommerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional
    public Usuario registrar(String nombre, String correo, String contraseña, String rolNombre) {
        if(usuarioRepository.existsByCorreo(correo)){
            throw new RuntimeException("El correo ya está registrado");
        }

        Rol rol = rolRepository.findByNombre(rolNombre).orElseThrow(() -> new RuntimeException("El rol no existe"));

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContraseña(passwordEncoder.encode(contraseña));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("El correo no se encuentra"));
    }
}
