package com.example.app_sus.repository;

import com.example.app_sus.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Opcional, mas boa prática

@Repository // Indica ao Spring que esta é uma interface de repositório
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // JpaRepository já fornece métodos CRUD básicos como:
    // save(), findById(), findAll(), deleteById(), etc.

    // Você pode adicionar métodos de consulta personalizados aqui, se necessário, por exemplo:
    // List<Medico> findByEspecialidade(String especialidade);
    // Optional<Medico> findByCrm(String crm);
}