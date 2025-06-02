package com.example.app_sus.repository;

import com.example.app_sus.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // ADICIONE ESTE IMPORT

@Repository // ADICIONE ESTA ANOTAÇÃO
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Aqui você pode adicionar métodos personalizados, se precisar
}