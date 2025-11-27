package com.peterson.gerenciador_tarefas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peterson.gerenciador_tarefas.dto.LoginDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioCadastroDTO;
import com.peterson.gerenciador_tarefas.dto.UsuarioDTO;
import com.peterson.gerenciador_tarefas.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Cadastro 
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dto ){
        UsuarioDTO criado = usuarioService.cadastrar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO dto){
        String token = usuarioService.login(dto);
        return ResponseEntity.ok(Map.of("token", token));

    }
}
