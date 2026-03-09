package com.example.GestorEnvios.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "configuracion")
@Data
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacidadDiaria;

}