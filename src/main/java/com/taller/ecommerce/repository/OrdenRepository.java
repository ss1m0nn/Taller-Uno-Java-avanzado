package com.taller.ecommerce.repository;

import com.taller.ecommerce.model.Orden;
import com.taller.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuario(Long usuarioId);
}
