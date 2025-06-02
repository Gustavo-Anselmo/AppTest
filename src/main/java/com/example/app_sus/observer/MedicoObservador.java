// src/main/java/com/example/app_sus/observer/MedicoObservador.java
package com.example.app_sus.observer;

import com.example.app_sus.model.Medico;
import com.example.app_sus.model.estado.EstadoMedico; // Importar

public interface MedicoObservador {
    void atualizar(Medico medico, EstadoMedico novoEstado);
}