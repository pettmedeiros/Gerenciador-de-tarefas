package com.peterson.gerenciador_tarefas.service;

import com.peterson.gerenciador_tarefas.dto.LoginDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioCadastroDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioDTO;
import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.repository.UsuarioRepository;
import com.peterson.gerenciador_tarefas.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public UsuarioDTO cadastrar (UsuarioCadastroDTO dto){
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("E-mail já cadastrado");
            }
            Usuario usuario =dto.toEntity();
                usuario.setSenha((encoder.encode(usuario.getSenha())));
                Usuario salvo = usuarioRepository.save(usuario);
                return new UsuarioDTO(salvo);
    }

    public String login(LoginDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                    .filter(u -> encoder.matches(dto.getSenha(), u.getSenha())) //compara as senhas da DTO com a do banco
                    .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválida"));

        return jwtUtil.gerarToken(usuario.getEmail()); //gera um token com o e-mail do usuário
    }

    public Usuario getUsuarioDoToken(String token){
        String email = jwtUtil.getEmail(token); //pega o email que vem do token
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));
    }
}
