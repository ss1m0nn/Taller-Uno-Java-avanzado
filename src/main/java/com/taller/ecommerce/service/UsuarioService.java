package com.taller.ecommerce.service;

import com.taller.ecommerce.dto.LoginDTO;
import com.taller.ecommerce.dto.UsuarioRegistroDTO;
import com.taller.ecommerce.exception.CredencialesInvalidasException;
import com.taller.ecommerce.model.Rol;
import com.taller.ecommerce.model.Usuario;
import com.taller.ecommerce.repository.RolRepository;
import com.taller.ecommerce.repository.UsuarioRepository;
import com.taller.ecommerce.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public Usuario registrar(UsuarioRegistroDTO dto) {
        if(usuarioRepository.existsByCorreo(dto.correo())){
            throw new RuntimeException("El correo ya está registrado");
        }

        Rol rol = rolRepository.findByNombre("CLIENTE").orElseThrow(() -> new RuntimeException("Rol no asignado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setContraseña(passwordEncoder.encode(dto.contraseña()));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    public String login(LoginDTO dto) {

        Usuario usuario = usuarioRepository.findByCorreo(dto.correo())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrecta"));

        if (!passwordEncoder.matches(dto.contraseña(), usuario.getContraseña())) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrecta");
        }

        return jwtService.generarToken(
                usuario.getCorreo(),
                usuario.getRol().getNombre()
        );
    }
}
