package com.taller.ecommerce.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    String nombre;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuario;

}
