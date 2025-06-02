// src/main/java/com/example/app_sus/model/estado/MedicoDeLicenca.java
package com.example.app_sus.model.estado;

import com.example.app_sus.model.Medico;

public class MedicoDeLicenca implements EstadoMedico {
    @Override
    public void solicitarFerias(Medico medico) {
        System.out.println("Não é possível solicitar férias. " + medico.getNome() + " está de licença médica.");
    }

    @Override
    public void entrarEmLicenca(Medico medico) {
        System.out.println(medico.getNome() + " já está de licença médica.");
    }

    @Override
    public void retornarAoTrabalho(Medico medico) {
        System.out.println(medico.getNome() + " está retornando da licença médica.");
        medico.setEstadoAtual(new MedicoDisponivel());
    }

    @Override
    public boolean podeAgendarConsulta(Medico medico) {
        System.out.println(medico.getNome() + " não pode agendar consultas, está de licença médica.");
        return false;
    }

    @Override
    public String getDescricaoEstado() {
        return "De Licença Médica";
    }
}