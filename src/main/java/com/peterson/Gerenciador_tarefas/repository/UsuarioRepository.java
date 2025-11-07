package com.peterson.gerenciador_tarefas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.peterson.gerenciador_tarefas.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{ //responsavel pela geração dos metodos CRUD automaticamente 

    Optional<Usuario> findByEmail(String email); //gera a query para buscar o usuario por email 
    
}
