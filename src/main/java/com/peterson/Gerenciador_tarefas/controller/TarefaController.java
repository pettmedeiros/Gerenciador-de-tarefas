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

import com.peterson.gerenciador_tarefas.dto.TarefaDTO;
import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.service.GerenciadorTarefas;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tarefas")
public class TarefaController {

    private final GerenciadorTarefas gerenciador;

    public TarefaController(GerenciadorTarefas gerenciador){
        this.gerenciador = gerenciador;
    }

    //Busca todas as tarefas
    @GetMapping
    public List<TarefaDTO> listar(){
        return gerenciador.listarTarefas()
        .stream()
        .map(TarefaDTO::new) //converte cada tarefa em tarefaDTO
        .toList();
    }

    //Buusca a tarefa pelo seu id
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscar(@PathVariable Long id){
        return gerenciador.buscarTarefaPorId(id)
        .map(t -> ResponseEntity.ok(new TarefaDTO(t))) //se encotrar tarefa, converse o DTO e da 200 ok
        .orElse(ResponseEntity.notFound().build()); //se não, da erro 404 (Not found)
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> criar(@Valid @RequestBody TarefaDTO dto){
        Tarefa tarefa = dto.toEntity();
        Tarefa salva = gerenciador.adicionarTarefa(tarefa);

        return ResponseEntity.status(201).body(new TarefaDTO(salva)); //retorna a atualização em DTO
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TarefaDTO> atualizarStatus(@Valid @PathVariable Long id, @RequestBody TaskStatus status){
        return gerenciador.buscarTarefaPorId(id)
        .map(t -> {
            t.atualizarStatus(status); // Assume que Tarefa tem esse método
            Tarefa atualizada = gerenciador.adicionarTarefa(t); //Salva a atualização 
            return ResponseEntity.ok(new TarefaDTO(atualizada)); //retorna dto
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@Valid @PathVariable Long id, @RequestBody TarefaDTO tarefaAtualizada) {
        return gerenciador.buscarTarefaPorId(id)
        .map(t -> {
            t.setTitulo(tarefaAtualizada.getTitulo());
            t.setDescricao(tarefaAtualizada.getDescricao());
            t.setDataPrevistaDate(tarefaAtualizada.getDataPrevistaDate());
            t.setStatus(tarefaAtualizada.getStatus());
            t.setPrioridade(tarefaAtualizada.getPrioridade());

            Tarefa atualizada = gerenciador.adicionarTarefa(t); 
            return ResponseEntity.ok(new TarefaDTO(atualizada));   //Retorna dto,não entidade
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> delete (@PathVariable Long id){
        gerenciador.removerTarefa(id);
        return ResponseEntity.ok().build();
    }
}
