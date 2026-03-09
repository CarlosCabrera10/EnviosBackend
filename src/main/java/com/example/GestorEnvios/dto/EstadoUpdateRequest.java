package com.example.GestorEnvios.dto;

import com.example.GestorEnvios.entity.EstadoEnvio;

public class EstadoUpdateRequest {
    
    private EstadoEnvio estado;

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }
}
