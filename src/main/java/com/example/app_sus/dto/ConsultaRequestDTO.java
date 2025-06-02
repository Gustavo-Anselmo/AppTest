package com.example.app_sus.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ConsultaRequestDTO {

    @NotNull(message = "ID do Paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "ID do Médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "Data e hora da consulta são obrigatórios")
    @FutureOrPresent(message = "A data da consulta não pode ser no passado")
    private LocalDateTime dataHora;

    private String descricao;

    // Getters e Setters
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}