// src/main/java/com/example/app_sus/model/estado/EstadoMedico.java
package com.example.app_sus.model.estado;

import com.example.app_sus.model.Medico;

public interface EstadoMedico {
    // Ação que pode variar conforme o estado
    void solicitarFerias(Medico medico);
    void entrarEmLicenca(Medico medico);
    void retornarAoTrabalho(Medico medico);
    boolean podeAgendarConsulta(Medico medico);
    String getDescricaoEstado();
}