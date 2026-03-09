package com.example.GestorEnvios.repository;

import com.example.GestorEnvios.entity.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {
}