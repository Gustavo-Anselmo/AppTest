// src/main/java/com/example/app_sus/model/Consulta.java
package com.example.app_sus.model;

import jakarta.persistence.*;
import com.example.app_sus.model.enums.StatusConsulta;
import java.time.LocalDateTime;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusConsulta statusConsulta; // Enum: AGENDADA, CANCELADA, REALIZADA

    public Consulta() {}

    // Getters, Setters, Construtor...
    // Id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    // DataHora
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    // Medico
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    // Paciente
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    // Descricao
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    // StatusConsulta
    public StatusConsulta getStatusConsulta() { return statusConsulta; }
    public void setStatusConsulta(StatusConsulta statusConsulta) { this.statusConsulta = statusConsulta; }
}