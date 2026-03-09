package com.example.GestorEnvios.controller;

import com.example.GestorEnvios.entity.Configuracion;
import com.example.GestorEnvios.entity.Envio;
import com.example.GestorEnvios.entity.Usuario;
import com.example.GestorEnvios.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    // Solicitar envío (ya lo tienes)
    @PostMapping("/solicitar")
    public Envio solicitarEnvio(@RequestBody Usuario usuario){
        return envioService.solicitarEnvio(usuario);
    }

    // Consultar estado por código
    @GetMapping("/{codigo}")
    public Envio buscarEnvio(@PathVariable String codigo){
        return envioService.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Código no encontrado"));
    }

    // Cancelar envío
    @PutMapping("/cancelar/{codigo}")
    public Envio cancelarEnvio(@PathVariable String codigo){
        return envioService.cancelarEnvio(codigo);
    }

    // Capacidad diaria (para habilitar botón en frontend)
    @GetMapping("/capacidad-actual")
    public String capacidadActual() {
        Configuracion config = envioService.obtenerConfiguracion();
        int capacidad = config.getCapacidadDiaria();
        int usados = envioService.contarEnviosHoy();
        return (usados >= capacidad) ? "lleno" : "disponible";
    }

    // Historial de envíos del usuario
    @GetMapping("/historial/{usuarioId}")
    public List<Envio> historialUsuario(@PathVariable Long usuarioId) {
        return envioService.obtenerHistorialUsuario(usuarioId);
    }
}