// src/main/java/com/example/app_sus/model/estado/MedicoEmFerias.java
package com.example.app_sus.model.estado;

import com.example.app_sus.model.Medico;

public class MedicoEmFerias implements EstadoMedico {
    @Override
    public void solicitarFerias(Medico medico) {
        System.out.println(medico.getNome() + " já está de férias.");
    }

    @Override
    public void entrarEmLicenca(Medico medico) {
        System.out.println("Não é possível entrar em licença. " + medico.getNome() + " está de férias. Primeiro precisa retornar.");
    }

    @Override
    public void retornarAoTrabalho(Medico medico) {
        System.out.println(medico.getNome() + " está retornando das férias.");
        medico.setEstadoAtual(new MedicoDisponivel());
    }

    @Override
    public boolean podeAgendarConsulta(Medico medico) {
        System.out.println(medico.getNome() + " não pode agendar consultas, está de férias.");
        return false;
    }

    @Override
    public String getDescricaoEstado() {
        return "Em Férias";
    }
}