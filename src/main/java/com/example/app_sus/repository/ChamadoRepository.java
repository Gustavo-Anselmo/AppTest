package com.example.app_sus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app_sus.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    // Pode ter consultas personalizadas depois
}
