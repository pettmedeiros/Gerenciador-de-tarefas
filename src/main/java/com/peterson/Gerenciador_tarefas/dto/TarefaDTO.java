package com.peterson.gerenciador_tarefas.dto;

import java.time.LocalDate;

import com.peterson.gerenciador_tarefas.entities.Tarefa;
import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TarefaDTO {

    private Long idTarefa;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String descricao;

    private LocalDate dataCriacao;

    private LocalDate dataPrevistaDate;

    @NotNull(message = "O status é obrigatório")
    private TaskStatus status;

    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    public TarefaDTO(){
    }
    
    // Construtor que converte uma ENTIDADE em DTO
    public TarefaDTO(Tarefa tarefa) {
        this.idTarefa = tarefa.getIdTarefa();
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.dataCriacao = tarefa.getDataCriacao();
        this.dataPrevistaDate = tarefa.getDataPrevistaDate();
        this.status = tarefa.getStatus();
        this.prioridade = tarefa.getPrioridade();
    }

    //Método que converte o DTO de volta para a ENTIDADE
    public Tarefa toEntity(){
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(this.titulo);
        tarefa.setDescricao(this.descricao);
        tarefa.setDataCriacao(LocalDate.now());
        tarefa.setDataPrevistaDate(this.dataPrevistaDate);
        tarefa.setStatus(this.status);
        tarefa.setPrioridade(this.prioridade);

        return tarefa;
    }

    public TarefaDTO(Long idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataPrevistaDate() {
        return dataPrevistaDate;
    }

    public void setDataPrevistaDate(LocalDate dataPrevistaDate) {
        this.dataPrevistaDate = dataPrevistaDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Long getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Long idTarefa) {
        this.idTarefa = idTarefa;
    }
}
