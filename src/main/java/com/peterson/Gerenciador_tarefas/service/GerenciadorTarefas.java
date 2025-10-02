package com.peterson.gerenciador_tarefas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.repository.TarefaRepository;

@Service
public class GerenciadorTarefas {

    private final TarefaRepository repository; //injetando respositório

    public GerenciadorTarefas(TarefaRepository repository){
        this.repository = repository;
    }

    public Tarefa adicionarTarefa(Tarefa novaTarefa){
        return repository.save(novaTarefa);
    }

    public void removerTarefa(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    public List<Tarefa> listarTarefas(){
        return repository.findAll();
    }

    public Optional<Tarefa> buscarTarefaPorId(Long id){
        return repository.findById(id);
    }

    public List<Tarefa> listarTarefasPorStatus(TaskStatus status){
        return repository.findByStatus(status);
    }

    public List<Tarefa> listarTarefasPorPrioridade(Prioridade prioridade){
        return repository.findByPrioridade(prioridade);
    }
}