package com.example.app_sus.model;

import jakarta.persistence.*; // Certifique-se que CascadeType está importado
import jakarta.validation.constraints.NotBlank;
import com.example.app_sus.model.enums.Papel;

@Entity
@Table(name = "usuarios") // Boa prática definir o nome da tabela explicitamente
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O username é obrigatório.")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String senha; // Lembre-se que esta senha será criptografada pelo PasswordEncoder no service/dataloader

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel;

    @Column(nullable = false)
    private boolean ativo = true;

    // RELACIONAMENTO CORRIGIDO COM CASCADE E ORPHANREMOVAL
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id") // referencedColumnName é opcional se o ID em Pessoa for 'id'
    private Pessoa pessoa;

    public Usuario() {
    }

    public Usuario(String username, String senha, Papel papel, Pessoa pessoa) {
        this.username = username;
        this.senha = senha; // A senha ainda não está criptografada aqui, será no service/dataloader
        this.papel = papel;
        this.pessoa = pessoa;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public boolean isAtivo() { // Getter padrão para boolean
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}