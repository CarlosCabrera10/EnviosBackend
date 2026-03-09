package com.example.GestorEnvios.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "envios")
@Data
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigo;

    private LocalDate fechaEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}