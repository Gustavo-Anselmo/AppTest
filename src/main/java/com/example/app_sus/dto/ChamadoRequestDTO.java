package com.example.app_sus.dto;

import com.example.app_sus.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class ChamadoRequestDTO {

    @NotNull(message = "ID do Paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "ID do Usuário (atendente) é obrigatório") // ADICIONADO
    private Long usuarioId;                                     // ADICIONADO

    @NotBlank(message = "Sintomas são obrigatórios")
    private String sintomas;

    private String observacoesAdicionais;

    @NotNull(message = "Endereço de atendimento é obrigatório")
    @Valid
    private Endereco enderecoAtendimento;

    // Getters e Setters
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getUsuarioId() { return usuarioId; } // ADICIONADO
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; } // ADICIONADO

    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }

    public String getObservacoesAdicionais() { return observacoesAdicionais; }
    public void setObservacoesAdicionais(String observacoesAdicionais) { this.observacoesAdicionais = observacoesAdicionais; }

    public Endereco getEnderecoAtendimento() { return enderecoAtendimento; }
    public void setEnderecoAtendimento(Endereco enderecoAtendimento) { this.enderecoAtendimento = enderecoAtendimento; }
}