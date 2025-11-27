package com.peterson.gerenciador_tarefas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peterson.gerenciador_tarefas.dto.TarefaDTO;
import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.service.GerenciadorTarefas;
import com.peterson.gerenciador_tarefas.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    private final GerenciadorTarefas gerenciador;
    private final UsuarioService usuarioService;

    public TarefaController(GerenciadorTarefas gerenciador, UsuarioService usuarioService){
        this.gerenciador = gerenciador;
        this.usuarioService = usuarioService;
    }

    private Usuario getUsuarioLogado (@RequestHeader("Authorization") String auth){

        String token = auth.replace("Bearer ", "");
        return usuarioService.getUsuarioDoToken(token);
    }

    //Busca todas as tarefas
    @GetMapping
    public List<TarefaDTO> listar(@RequestHeader("Authorization") String auth){
        Usuario usuario = getUsuarioLogado(auth);
        return gerenciador.listarTarefasDoUsuario(usuario)
                .stream()
                .map(TarefaDTO::new) //converte cada tarefa em tarefaDTO
                .toList();
    }

    //Busca a tarefa pelo seu id
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscar(@PathVariable Long id, @RequestHeader("Authorization") String auth){
        Usuario usuario = getUsuarioLogado(auth);
        return gerenciador.buscarTarefaDoUsuario(id, usuario)
                .map(t -> ResponseEntity.ok(new TarefaDTO(t))) //se encotrar tarefa, converse o DTO e da 200 ok
                .orElse(ResponseEntity.notFound().build()); //se não, da erro 404 (Not found)
    }

    //Busca Tarefa por status pendente
    @GetMapping("/pendentes")
    public List<TarefaDTO> pendentes(@RequestHeader("Authorization") String auth) {
        Usuario usuario = getUsuarioLogado(auth);
        return gerenciador.listarPendentes(usuario)
                .stream()
                .map(TarefaDTO::new)
                .toList();
    }

    //Busca Tarefa por status concluida
    @GetMapping("/concluidas")
    public List<TarefaDTO> concluidas(@RequestHeader("Authorization") String auth) {
        Usuario usuario = getUsuarioLogado(auth);
        return gerenciador.listarConcluidas(usuario)
                .stream()
                .map(TarefaDTO::new)
                .toList();
    }

    //Busca Tarefa por prioridade alta
    @GetMapping("/alta")
    public List<TarefaDTO> altaPrioridade(@RequestHeader("Authorization") String auth) {
        Usuario usuario = getUsuarioLogado(auth);
        return gerenciador.listarAltaPrioridade(usuario)
                .stream()
                .map(TarefaDTO::new)
                .toList();
    }


    //Criar Tarefa
    @PostMapping
    public ResponseEntity<TarefaDTO> criar(@Valid @RequestBody TarefaDTO dto, @RequestHeader("Authorization") String auth){
        Usuario usuario = getUsuarioLogado(auth);
        Tarefa tarefa = gerenciador.adicionarTarefa(dto, usuario);

        return ResponseEntity.status(201).body(new TarefaDTO(tarefa)); //retorna a atualização em DTO
    }

    //Atualizar status
    @PutMapping("/{id}/status")
    public ResponseEntity<TarefaDTO> atualizarStatus(@Valid @PathVariable Long id, @RequestBody TaskStatus status, @RequestHeader("Authorization") String auth){

        Usuario usuario = getUsuarioLogado(auth);
        try {
            Tarefa tarefa = gerenciador.atualizarStatus(id, status, usuario);
            return ResponseEntity.ok(new TarefaDTO(tarefa));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Atualizar tudo 
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@Valid @PathVariable Long id, @RequestBody TarefaDTO tarefaAtualizada, @RequestHeader("Authorization") String auth) {
        Usuario usuario = getUsuarioLogado(auth);
        try {
            Tarefa tarefa = gerenciador.atualizarTarefa(id, tarefaAtualizada , usuario);
                return ResponseEntity.ok(new TarefaDTO(tarefa));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> delete (@PathVariable Long id, @RequestHeader("Authorization") String auth){
        Usuario usuario = getUsuarioLogado(auth);
        try {
            gerenciador.removerTarefa(id, usuario);
            return ResponseEntity.noContent().build();
        } catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
       
    }
}
