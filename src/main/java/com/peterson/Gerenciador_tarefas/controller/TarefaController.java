package com.peterson.gerenciador_tarefas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.service.GerenciadorTarefas;

@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    private final GerenciadorTarefas gerenciador;

    public TarefaController(GerenciadorTarefas gerenciador){
        this.gerenciador = gerenciador;
    }

    @GetMapping
    public List<Tarefa> listar(){
        return gerenciador.listarTarefas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id){
        return gerenciador.buscarTarefaPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa){
        Tarefa tarefaSalva = gerenciador.adicionarTarefa(tarefa);
        return ResponseEntity.status(201).body(tarefaSalva);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(@PathVariable Long id, @RequestBody TaskStatus status){
        return gerenciador.buscarTarefaPorId(id).map(t -> {
            t.atualizarStatus(status); // Assume que Tarefa tem esse método
            Tarefa atualizada = gerenciador.adicionarTarefa(t); //Salva a atualização 
            return ResponseEntity.ok(atualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return gerenciador.buscarTarefaPorId(id).map(t -> {
            t.setTitulo(tarefaAtualizada.getTitulo());
            t.setDescricao(tarefaAtualizada.getDescricao());
            t.setDataCriacao(tarefaAtualizada.getDataCriacao());
            t.setDataPrevistaDate(tarefaAtualizada.getDataPrevistaDate());
            t.setStatus(tarefaAtualizada.getStatus());
            t.setPrioridade(tarefaAtualizada.getPrioridade());

            Tarefa atualizada = gerenciador.adicionarTarefa(t); 
            return ResponseEntity.ok(atualizada);   // agora funciona
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> delete (@PathVariable Long id){
        gerenciador.removerTarefa(id);
        return ResponseEntity.ok().build();
    }
}
