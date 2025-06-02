package com.example.app_sus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app_sus.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}

