// src/main/java/com/example/app_sus/model/estado/MedicoDisponivel.java
package com.example.app_sus.model.estado;

import com.example.app_sus.model.Medico;

public class MedicoDisponivel implements EstadoMedico {
    @Override
    public void solicitarFerias(Medico medico) {
        System.out.println(medico.getNome() + " está solicitando férias.");
        medico.setEstadoAtual(new MedicoEmFerias()); // Muda o estado
    }

    @Override
    public void entrarEmLicenca(Medico medico) {
        System.out.println(medico.getNome() + " está entrando de licença médica.");
        medico.setEstadoAtual(new MedicoDeLicenca()); // Muda o estado
    }

    @Override
    public void retornarAoTrabalho(Medico medico) {
        System.out.println(medico.getNome() + " já está disponível.");
    }

    @Override
    public boolean podeAgendarConsulta(Medico medico) {
        return true;
    }

    @Override
    public String getDescricaoEstado() {
        return "Disponível";
    }
}