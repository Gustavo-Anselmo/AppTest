package com.example.app_sus.dto;

import com.example.app_sus.model.Endereco; // Assumindo que Pessoa terá Endereco
import com.example.app_sus.model.enums.Papel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class UsuarioRequestDTO {

    @NotBlank(message = "O nome de usuário (username) é obrigatório.")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres.")
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @NotNull(message = "O papel do usuário é obrigatório.")
    private Papel papel; // ADMIN, MEDICO, AUXILIAR

    // Campos para a Pessoa associada
    @NotBlank(message = "O nome completo da pessoa é obrigatório.")
    private String nomePessoa;

    @NotBlank(message = "O CPF da pessoa é obrigatório.")
    private String cpfPessoa;

    @NotNull(message = "A data de nascimento da pessoa é obrigatória.")
    @PastOrPresent(message = "A data de nascimento não pode ser no futuro.")
    private LocalDate dataDeNascimentoPessoa;

    @NotBlank(message = "O telefone de contato da pessoa é obrigatório.")
    private String telefoneContatoPessoa;

    @NotBlank(message = "O email de contato da pessoa é obrigatório.")
    @Email(message = "O email da pessoa deve ter um formato válido.")
    private String emailContatoPessoa;

    @NotNull(message = "O endereço da pessoa é obrigatório.")
    @Valid // Para validar os campos dentro de Endereco
    private Endereco enderecoPessoa;


    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Papel getPapel() { return papel; }
    public void setPapel(Papel papel) { this.papel = papel; }
    public String getNomePessoa() { return nomePessoa; }
    public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }
    public String getCpfPessoa() { return cpfPessoa; }
    public void setCpfPessoa(String cpfPessoa) { this.cpfPessoa = cpfPessoa; }
    public LocalDate getDataDeNascimentoPessoa() { return dataDeNascimentoPessoa; }
    public void setDataDeNascimentoPessoa(LocalDate dataDeNascimentoPessoa) { this.dataDeNascimentoPessoa = dataDeNascimentoPessoa; }
    public String getTelefoneContatoPessoa() { return telefoneContatoPessoa; }
    public void setTelefoneContatoPessoa(String telefoneContatoPessoa) { this.telefoneContatoPessoa = telefoneContatoPessoa; }
    public String getEmailContatoPessoa() { return emailContatoPessoa; }
    public void setEmailContatoPessoa(String emailContatoPessoa) { this.emailContatoPessoa = emailContatoPessoa; }
    public Endereco getEnderecoPessoa() { return enderecoPessoa; }
    public void setEnderecoPessoa(Endereco enderecoPessoa) { this.enderecoPessoa = enderecoPessoa; }
}