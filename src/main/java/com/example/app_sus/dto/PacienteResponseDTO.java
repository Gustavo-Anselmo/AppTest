package com.example.app_sus.dto;

import com.example.app_sus.model.Endereco; // Pode ser EnderecoDTO se você tiver um
import com.example.app_sus.model.Paciente;
import com.example.app_sus.model.enums.TipoDeDeficiencia;

import java.time.LocalDate;

public class PacienteResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataDeNascimento;
    private String telefoneContato;
    private String emailContato;
    private Endereco endereco; // Ou EnderecoDTO
    private boolean possuiDeficiencia;
    private TipoDeDeficiencia tipoDeDeficiencia;
    private String observacoesDeficiencia;

    public PacienteResponseDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.cpf = paciente.getCpf(); // Considere se quer expor
        this.dataDeNascimento = paciente.getDataDeNascimento();
        this.telefoneContato = paciente.getTelefoneContato();
        this.emailContato = paciente.getEmailContato();
        this.endereco = paciente.getEndereco(); // Mapeie para EnderecoDTO se tiver
        this.possuiDeficiencia = paciente.getPossuiDeficiencia();
        this.tipoDeDeficiencia = paciente.getTipoDeDeficiencia();
        this.observacoesDeficiencia = paciente.getObservacoesDeficiencia();
    }

    // Getters para todos os campos (necessário para Jackson/frontend)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataDeNascimento() { return dataDeNascimento; }
    public String getTelefoneContato() { return telefoneContato; }
    public String getEmailContato() { return emailContato; }
    public Endereco getEndereco() { return endereco; }
    public boolean isPossuiDeficiencia() { return possuiDeficiencia; } // Getter para boolean
    public TipoDeDeficiencia getTipoDeDeficiencia() { return tipoDeDeficiencia; }
    public String getObservacoesDeficiencia() { return observacoesDeficiencia; }
}