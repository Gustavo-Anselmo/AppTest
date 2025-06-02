// src/main/java/com/example/app_sus/repository/ConsultaRepository.java
package com.example.app_sus.repository;

import com.example.app_sus.model.Consulta;
import com.example.app_sus.model.Medico;
import com.example.app_sus.model.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoAndStatusConsulta(Medico medico, StatusConsulta status);
}