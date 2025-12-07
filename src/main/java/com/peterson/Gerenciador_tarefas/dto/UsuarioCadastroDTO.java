package com.peterson.gerenciador_tarefas.dto;

import com.peterson.gerenciador_tarefas.entities.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioCadastroDTO {

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;
    
    public UsuarioCadastroDTO() {
    }

    // Construtor que transforma ENTIDADE -> DTO (uso ao retornar dados)
    public UsuarioCadastroDTO(Usuario usuario) {
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
    }

    // Converte este DTO em uma ENTIDADE (DTO -> Entity)
    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setNome(this.nome);
        usuario.setCpf(this.cpf);

        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
