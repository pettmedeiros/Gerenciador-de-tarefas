package com.peterson.gerenciador_tarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
    List<Tarefa> findByStatus(TaskStatus status);
    List<Tarefa> findByPrioridade(Prioridade prioridade);    
} 