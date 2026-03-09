package com.example.GestorEnvios.repository;

import com.example.GestorEnvios.entity.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GestorEnvios.entity.EstadoEnvio;

import com.example.GestorEnvios.entity.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

    int countByFechaEnvio(LocalDate fechaEnvio);

    int countByFechaEnvioAndEstadoNotIn(LocalDate fechaEnvio, List<EstadoEnvio> estados);

    Optional<Envio> findByCodigo(String codigo);

    List<Envio> findByUsuarioIdOrderByFechaEnvioDesc(Long usuarioId);

}