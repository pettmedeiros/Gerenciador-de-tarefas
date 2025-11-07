package com.peterson.gerenciador_tarefas.service;

import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public Usuario cadastrar(String nome, String cpf, String email, String senha){
        String SenhaCriptografada = encoder.encode(senha);
        Usuario novoUsuario = new Usuario(nome, cpf, email, SenhaCriptografada);
        return usuarioRepository.save( novoUsuario);
    }
    
    public Optional<Usuario> autenticar(String email, String senha){
        return usuarioRepository.findByEmail(email).filter(u -> encoder.matches(senha, u.getSenha()));
    }
}
