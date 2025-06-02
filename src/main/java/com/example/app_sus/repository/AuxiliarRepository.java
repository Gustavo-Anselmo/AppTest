package com.example.app_sus.repository;

import com.example.app_sus.model.Auxiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuxiliarRepository extends JpaRepository<Auxiliar, Long> {
}