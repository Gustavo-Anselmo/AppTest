package com.example.app_sus.dto;

import com.example.app_sus.model.Medico;

public class MedicoResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String crm;
    private String especialidade;
    private String estadoAtualDescricao;

    public MedicoResponseDTO(Medico medico) {
        this.id = medico.getId();
        this.nome = medico.getNome();
        this.cpf = medico.getCpf(); // Considere se quer expor o CPF aqui
        this.crm = medico.getCrm();
        this.especialidade = medico.getEspecialidade();
        this.estadoAtualDescricao = medico.getDescricaoEstadoAtual();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getEstadoAtualDescricao() {
        return estadoAtualDescricao;
    }
}