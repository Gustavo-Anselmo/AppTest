// src/main/java/com/example/app_sus/service/GerenciadorDeAgendaService.java
package com.example.app_sus.service;

import com.example.app_sus.model.Consulta;
import com.example.app_sus.model.Medico;
import com.example.app_sus.model.enums.StatusConsulta;
import com.example.app_sus.model.estado.EstadoMedico;
import com.example.app_sus.model.estado.MedicoDeLicenca;
import com.example.app_sus.observer.MedicoObservador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GerenciadorDeAgendaService implements MedicoObservador {

    private final ConsultaService consultaService;

    @Autowired
    public GerenciadorDeAgendaService(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @Override
    public void atualizar(Medico medico, EstadoMedico novoEstado) {
        System.out.println("GerenciadorDeAgendaService notificado sobre " + medico.getNome() +
                           ". Novo estado: " + novoEstado.getDescricaoEstado());
        if (novoEstado instanceof MedicoDeLicenca) {
            System.out.println("AÇÃO: Médico " + medico.getNome() + " entrou de licença. Verificando agenda...");
            
            // LINHA CORRIGIDA AQUI:
            List<Consulta> consultasAgendadas = consultaService.buscarTodasConsultasPorMedico(medico);
            
            if (consultasAgendadas.isEmpty()) {
                System.out.println("  -> Nenhuma consulta agendada para remarcar/cancelar para o médico: " + medico.getNome());
                return;
            }

            for (Consulta consulta : consultasAgendadas) {
                System.out.println("  -> Gerenciando consulta ID " + consulta.getId() +
                                   " com paciente " + (consulta.getPaciente() != null ? consulta.getPaciente().getNome() : "N/A") +
                                   " do dia " + consulta.getDataHora());
                
                consulta.setStatusConsulta(StatusConsulta.CANCELADA_MEDICO);
                consultaService.salvar(consulta);
                System.out.println("     Consulta ID " + consulta.getId() + " foi CANCELADA devido à licença do médico.");
            }
        }
    }
}