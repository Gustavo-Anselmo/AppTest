package com.example.app_sus.dto;

import com.example.app_sus.model.Usuario;
import com.example.app_sus.model.enums.Papel;

public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private Papel papel;
    private boolean ativo;
    private String nomePessoa; // Nome da Pessoa associada
    private Long pessoaId;     // ID da Pessoa associada

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.papel = usuario.getPapel();
        this.ativo = usuario.isAtivo();
        if (usuario.getPessoa() != null) {
            this.nomePessoa = usuario.getPessoa().getNome();
            this.pessoaId = usuario.getPessoa().getId();
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public Papel getPapel() { return papel; }
    public boolean isAtivo() { return ativo; }
    public String getNomePessoa() { return nomePessoa; }
    public Long getPessoaId() { return pessoaId; }
}