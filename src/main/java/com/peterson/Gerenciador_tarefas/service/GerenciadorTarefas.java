package com.peterson.gerenciador_tarefas.service;

import com.peterson.gerenciador_tarefas.dto.TarefaDTO;
import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.Usuario;
import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;
import com.peterson.gerenciador_tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GerenciadorTarefas {

    private final TarefaRepository repository;

    public GerenciadorTarefas(TarefaRepository repository) {
        this.repository = repository;
    }

    public Tarefa adicionarTarefa(TarefaDTO dto, Usuario usuario) {
        Tarefa tarefa = dto.toEntity(usuario);
        return repository.save(tarefa);
    }

    public List<Tarefa> listarTarefasDoUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    public Optional<Tarefa> buscarTarefaDoUsuario(Long id, Usuario usuario) {
        return repository.findById(id)
                .filter(t -> t.getUsuario().equals(usuario));
    }

    public Tarefa atualizarTarefa(Long id, TarefaDTO dto, Usuario usuario) {
        return buscarTarefaDoUsuario(id, usuario)
                .map(tarefa -> {
                    tarefa.setTitulo(dto.getTitulo());
                    tarefa.setDescricao(dto.getDescricao());
                    tarefa.setDataPrevistaDate(dto.getDataPrevistaDate());
                    tarefa.setStatus(dto.getStatus());
                    tarefa.setPrioridade(dto.getPrioridade());
                    return repository.save(tarefa);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada ou não pertence a você"));
    }

    public Tarefa atualizarStatus(Long id, TaskStatus status, Usuario usuario) {
        return buscarTarefaDoUsuario(id, usuario)
                .map(tarefa -> {
                    tarefa.atualizarStatus(status);
                    return repository.save(tarefa);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada ou não pertence a você"));
    }

    public void removerTarefa(Long id, Usuario usuario) {
        buscarTarefaDoUsuario(id, usuario)
                .ifPresentOrElse(
                    repository::delete,
                    () -> { throw new IllegalArgumentException("Tarefa não encontrada ou não pertence a você"); }
                );
    }

    // === FILTROS COM ENUMS (USADOS APÓS LOGIN) ===

    public List<Tarefa> listarPendentes(Usuario usuario) {
        return listarTarefasDoUsuario(usuario).stream()
                .filter(t -> t.getStatus() == TaskStatus.PENDENTE)
                .toList();
    }

    public List<Tarefa> listarConcluidas(Usuario usuario) {
        return listarTarefasDoUsuario(usuario).stream()
                .filter(t -> t.getStatus() == TaskStatus.CONCLUIDA)
                .toList();
    }

    public List<Tarefa> listarAltaPrioridade(Usuario usuario) {
        return listarTarefasDoUsuario(usuario).stream()
                .filter(t -> t.getPrioridade() == Prioridade.ALTA)
                .toList();
    }

    public List<Tarefa> listarBaixaPrioridade(Usuario usuario) {
        return listarTarefasDoUsuario(usuario).stream()
                .filter(t -> t.getPrioridade() == Prioridade.BAIXA)
                .toList();
    }
}