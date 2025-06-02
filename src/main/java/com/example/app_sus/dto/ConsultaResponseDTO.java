package com.example.app_sus.dto;

import com.example.app_sus.model.Consulta;
import java.time.LocalDateTime;

public class ConsultaResponseDTO {
    private Long id;
    private String pacienteNome;
    private Long pacienteId;
    private String medicoNome;
    private Long medicoId;
    private LocalDateTime dataHora;
    private String descricao;
    private String statusConsulta;

    public ConsultaResponseDTO(Consulta consulta) {
        this.id = consulta.getId();
        if (consulta.getPaciente() != null) {
            this.pacienteNome = consulta.getPaciente().getNome();
            this.pacienteId = consulta.getPaciente().getId();
        }
        if (consulta.getMedico() != null) {
            this.medicoNome = consulta.getMedico().getNome();
            this.medicoId = consulta.getMedico().getId();
        }
        this.dataHora = consulta.getDataHora();
        this.descricao = consulta.getDescricao();
        if (consulta.getStatusConsulta() != null) {
            this.statusConsulta = consulta.getStatusConsulta().toString(); // Ou uma descrição mais amigável
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getPacienteNome() { return pacienteNome; }
    public Long getPacienteId() { return pacienteId; }
    public String getMedicoNome() { return medicoNome; }
    public Long getMedicoId() { return medicoId; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getDescricao() { return descricao; }
    public String getStatusConsulta() { return statusConsulta; }
}