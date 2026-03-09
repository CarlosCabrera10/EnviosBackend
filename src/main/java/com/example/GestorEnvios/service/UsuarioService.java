package com.example.GestorEnvios.service;

import com.example.GestorEnvios.entity.Usuario;
import com.example.GestorEnvios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {

        Optional<Usuario> existente = usuarioRepository.findByCorreo(usuario.getCorreo());

        if (existente.isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setRol("CLIENTE");

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String correo, String password){

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

        if(usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();

            if(usuario.getPassword().equals(password)){
                return usuario;
            }
        }

        throw new RuntimeException("Credenciales incorrectas");
    }
}