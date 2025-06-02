package com.example.app_sus.model;

import jakarta.persistence.*;
import com.example.app_sus.model.enums.StatusChamado;

import java.time.LocalDateTime;

@Entity
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_atendimento_id", referencedColumnName = "id", nullable = false)
    private Endereco enderecoAtendimento;

    private String sintomas; // Campo mencionado no aviso

    private String observacoesAdicionais; // Campo mencionado no aviso

    private LocalDateTime dataHoraChamado; // Campo mencionado no aviso

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;

    public Chamado() {
        this.dataHoraChamado = LocalDateTime.now();
        this.status = StatusChamado.NOVO;
    }

    public Chamado(Usuario usuario, Paciente paciente, Endereco enderecoAtendimento,
                   String sintomas, String observacoesAdicionais) {
        this(); // Chama o construtor padrão para setar dataHoraChamado e status
        this.usuario = usuario;
        this.paciente = paciente;
        this.enderecoAtendimento = enderecoAtendimento;
        this.sintomas = sintomas;
        this.observacoesAdicionais = observacoesAdicionais;
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Endereco getEnderecoAtendimento() {
        return enderecoAtendimento;
    }

    public void setEnderecoAtendimento(Endereco enderecoAtendimento) {
        this.enderecoAtendimento = enderecoAtendimento;
    }

    // GETTER PARA SINTOMAS
    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    // GETTER PARA OBSERVACOES ADICIONAIS
    public String getObservacoesAdicionais() {
        return observacoesAdicionais;
    }

    public void setObservacoesAdicionais(String observacoesAdicionais) {
        this.observacoesAdicionais = observacoesAdicionais;
    }

    // GETTER PARA DATAHORACHAMADO
    public LocalDateTime getDataHoraChamado() {
        return dataHoraChamado;
    }

    public void setDataHoraChamado(LocalDateTime dataHoraChamado) {
        this.dataHoraChamado = dataHoraChamado;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    // Métodos de negócio (do padrão State ou outros) podem vir aqui
    // Exemplo:
    // public boolean podeSerCancelado() {
    //     return this.status == StatusChamado.NOVO || this.status == StatusChamado.EM_ANDAMENTO;
    // }
}