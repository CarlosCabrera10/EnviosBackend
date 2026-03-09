package com.example.GestorEnvios.service;

import com.example.GestorEnvios.entity.Configuracion;
import com.example.GestorEnvios.entity.Envio;
import com.example.GestorEnvios.entity.EstadoEnvio;
import com.example.GestorEnvios.entity.Usuario;
import com.example.GestorEnvios.repository.ConfiguracionRepository;
import com.example.GestorEnvios.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    // Solicitar envío
    public Envio solicitarEnvio(Usuario usuario){
        Configuracion config = configuracionRepository.findAll().get(0);
        int capacidad = config.getCapacidadDiaria();
        LocalDate fecha = LocalDate.now();

        while(true){
            int total = envioRepository.countByFechaEnvio(fecha);
            if(total < capacidad) break;
            fecha = fecha.plusDays(1);
        }

        Envio envio = new Envio();
        envio.setCodigo("ENV-" + UUID.randomUUID().toString().substring(0,8));
        envio.setFechaEnvio(fecha);
        envio.setEstado(EstadoEnvio.AGENDADO);
        envio.setUsuario(usuario);

        return envioRepository.save(envio);
    }

    // Consultar envío
    public Optional<Envio> buscarPorCodigo(String codigo){
        return envioRepository.findByCodigo(codigo);
    }

    // Cancelar envío solo si está Agendado
    public Envio cancelarEnvio(String codigo){
        Envio envio = envioRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Código no encontrado"));

        if(envio.getEstado() != EstadoEnvio.AGENDADO){
            throw new RuntimeException("Solo envíos agendados pueden cancelarse");
        }

        envio.setEstado(EstadoEnvio.CANCELADO);
        return envioRepository.save(envio);
    }

    // Métodos auxiliares
    public Configuracion obtenerConfiguracion(){
        return configuracionRepository.findAll().get(0);
    }

    public int contarEnviosHoy(){
        return envioRepository.countByFechaEnvio(LocalDate.now());
    }

    // Obtener historial de envíos del usuario
    public List<Envio> obtenerHistorialUsuario(Long usuarioId){
        return envioRepository.findByUsuarioIdOrderByFechaEnvioDesc(usuarioId);
    }
}