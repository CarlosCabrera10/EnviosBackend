package com.example.GestorEnvios.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String sexo;

    @Column(unique = true)
    private String correo;

    private String telefono;

    private String password;

    private String rol;

}