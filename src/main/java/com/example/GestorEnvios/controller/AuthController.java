package com.example.GestorEnvios.controller;

import com.example.GestorEnvios.entity.Usuario;
import com.example.GestorEnvios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario usuario){
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario){
        return usuarioService.login(usuario.getCorreo(), usuario.getPassword());
    }
}