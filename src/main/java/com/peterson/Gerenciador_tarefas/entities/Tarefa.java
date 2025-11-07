package com.peterson.gerenciador_tarefas.entities;

import java.time.LocalDate;

import com.peterson.gerenciador_tarefas.entities.enums.Prioridade;
import com.peterson.gerenciador_tarefas.entities.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarefa;

    @Column(nullable = false, length = 100) //não acc campo vazio e tamanho maximo de 100 caracteres
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(name="data_criação", nullable = false)
    private LocalDate dataCriacao;

    @Column(name="data_prevista")
    private LocalDate dataPrevistaDate;

    @Enumerated(EnumType.STRING)//armazena o valor em forma de texto
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)//armazena o valor em forma de texto
    @Column(nullable = false)    
    private Prioridade prioridade;

    public Tarefa(){ //instancia automaticamente a data atual e status pendente
        this.dataCriacao = LocalDate.now();
        this.status = TaskStatus.PENDENTE;
    }

    public Tarefa(Long idTarefa, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataPrevistaDate,
        TaskStatus status, Prioridade prioridade) {
        this.idTarefa = idTarefa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataPrevistaDate = dataPrevistaDate;
        this.status = status;
        this.prioridade = prioridade;
    }

    public Long getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Long idTarefa) {
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

    public void atualizarStatus(TaskStatus novoStatus){
        this.status = novoStatus;
    }

    @Override
    public String toString() {
        return "Tarefa [idTarefa=" + idTarefa + 
                ", titulo=" + titulo + 
                ", dataCriacao=" + dataCriacao + 
                ", status=" + status +
                ", prioridade=" + prioridade + "]";
    }
    
    
}