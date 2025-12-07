package com.peterson.gerenciador_tarefas.dto;

import com.peterson.gerenciador_tarefas.entities.Usuario;

public class UsuarioDTO{
    private Long id;
    private String email;
    
    public UsuarioDTO() {
    }

    // Construtor que transforma ENTIDADE -> DTO (uso ao retornar dados)
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
    }

    // Converte este DTO em uma ENTIDADE (DTO -> Entity)
    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setEmail(this.email);
       
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
