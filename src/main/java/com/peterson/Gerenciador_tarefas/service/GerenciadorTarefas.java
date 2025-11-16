package com.peterson.gerenciador_tarefas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.peterson.gerenciador_tarefas.dto.TarefaDTO;
import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.repository.TarefaRepository;
import com.peterson.gerenciador_tarefas.repository.UsuarioRepository;

@Service
public class GerenciadorTarefas {

    private final TarefaRepository repository; //injetando respositório
    

    public GerenciadorTarefas(TarefaRepository repository){
        this.repository = repository;

    }

    //criar tarefa
    public Tarefa adicionarTarefa(TarefaDTO dto, Usuario usuario){
        Tarefa tarefa = dto.toEntity(usuario);
        return repository.save(tarefa);
    }

    public void removerTarefa(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }



   // Para buscar uma tarefa com segurança
    public Optional<Tarefa> buscarTarefaDoUsuario(Long id, Usuario usuario) {
        return repository.findById(id)
                .filter(t -> t.getUsuario().equals(usuario));
    }
}