package com.taller.ecommerce.service;

import com.taller.ecommerce.model.Auditoria;
import com.taller.ecommerce.repository.AuditoriaRepository;
import com.taller.ecommerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaService {
    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository, UsuarioRepository usuarioRepository) {
        this.auditoriaRepository = auditoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void registrar(String accion, String entidad, Long entidadId, String correo, String detalle) {

        Auditoria a = new Auditoria();
        a.setAccion(accion);
        a.setEntidad(entidad);
        a.setEntidadId(entidadId);
        a.setUsuario(usuarioRepository.findByCorreo(correo).orElse(null));
        a.setFecha(LocalDateTime.now());
        a.setDetalle(detalle);

        auditoriaRepository.save(a);
    }
}
