package com.example.app_sus.dto;

import com.example.app_sus.model.Chamado;
import com.example.app_sus.model.Endereco; // Ou um EnderecoDTO se preferir
import com.example.app_sus.model.enums.StatusChamado;

import java.time.LocalDateTime;

public class ChamadoResponseDTO {
    private Long id;
    private Long pacienteId;
    private String pacienteNome;
    private Long usuarioId; // ID do usuário que registrou (opcional, mas pode ser útil)
    private String usuarioUsername; // Username do usuário
    private Endereco enderecoAtendimento; // Ou EnderecoDTO
    private String sintomas;
    private String observacoesAdicionais;
    private LocalDateTime dataHoraChamado;
    private StatusChamado status;

    public ChamadoResponseDTO(Chamado chamado) {
        this.id = chamado.getId();
        if (chamado.getPaciente() != null) {
            this.pacienteId = chamado.getPaciente().getId();
            this.pacienteNome = chamado.getPaciente().getNome();
        }
        if (chamado.getUsuario() != null) {
            this.usuarioId = chamado.getUsuario().getId();
            this.usuarioUsername = chamado.getUsuario().getUsername();
        }
        this.enderecoAtendimento = chamado.getEnderecoAtendimento();
        this.sintomas = chamado.getSintomas();
        this.observacoesAdicionais = chamado.getObservacoesAdicionais();
        this.dataHoraChamado = chamado.getDataHoraChamado();
        this.status = chamado.getStatus();
    }

    // Getters para todos os campos
    public Long getId() { return id; }
    public Long getPacienteId() { return pacienteId; }
    public String getPacienteNome() { return pacienteNome; }
    public Long getUsuarioId() { return usuarioId; }
    public String getUsuarioUsername() { return usuarioUsername; }
    public Endereco getEnderecoAtendimento() { return enderecoAtendimento; }
    public String getSintomas() { return sintomas; }
    public String getObservacoesAdicionais() { return observacoesAdicionais; }
    public LocalDateTime getDataHoraChamado() { return dataHoraChamado; }
    public StatusChamado getStatus() { return status; }
}