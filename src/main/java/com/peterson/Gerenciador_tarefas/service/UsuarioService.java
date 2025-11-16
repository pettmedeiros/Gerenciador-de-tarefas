package com.peterson.gerenciador_tarefas.service;

import com.peterson.gerenciador_tarefas.dto.LoginDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioCadastroDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioDTO;
import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
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
    
    public Optional<UsuarioDTO> autenticar(LoginDTO dto){
        return usuarioRepository.findByEmail(dto.getEmail())
                .filter(u-> encoder.matches(dto.getSenha(), u.getSenha()))
                .map(UsuarioDTO::new);
    }
}
