package com.example.GestorEnvios.controller;

import com.example.GestorEnvios.dto.EstadoUpdateRequest;
import com.example.GestorEnvios.entity.Configuracion;
import com.example.GestorEnvios.entity.Envio;
import com.example.GestorEnvios.entity.EstadoEnvio;
import com.example.GestorEnvios.repository.ConfiguracionRepository;
import com.example.GestorEnvios.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    @Autowired
    private EnvioRepository envioRepository;

    // GET listar todos los envíos
    @GetMapping("/envios")
    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    // GET configuración
    @GetMapping("/config")
    public Configuracion obtenerConfig() {
        return configuracionRepository.findAll().get(0); // asumimos solo 1 registro
    }

    // PUT configuración (actualizar capacidad)
    @PutMapping("/config")
    public Configuracion actualizarConfig(@RequestBody Configuracion configuracion){
        Configuracion existente = configuracionRepository.findAll().get(0);
        existente.setCapacidadDiaria(configuracion.getCapacidadDiaria());
        return configuracionRepository.save(existente);
    }

    // PUT actualizar estado del envío
    @PutMapping("/estado/{codigo}")
    public Envio actualizarEstado(@PathVariable String codigo,
                                  @RequestBody EstadoUpdateRequest request){

        Envio envio = envioRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));

        if(envio.getEstado() == EstadoEnvio.RECIBIDO || envio.getEstado() == EstadoEnvio.CANCELADO){
            throw new RuntimeException("No se puede modificar este envio");
        }

        envio.setEstado(request.getEstado());

        return envioRepository.save(envio);

    }

    @GetMapping("/capacidad-actual")
    public String capacidadActual() {
        List<Configuracion> configs = configuracionRepository.findAll();
        if(configs.isEmpty() || configs.get(0).getCapacidadDiaria() == null || configs.get(0).getCapacidadDiaria() <= 0){
            return "disponible";
        }

        Configuracion config = configs.get(0);
        int capacidad = config.getCapacidadDiaria();
        int usados = envioRepository.countByFechaEnvioAndEstadoNotIn(
            LocalDate.now(),
            List.of(EstadoEnvio.CANCELADO, EstadoEnvio.RECIBIDO)
        );

        return usados >= capacidad ? "lleno" : "disponible";
    }

}