package com.example.app_sus.dto;

import com.example.app_sus.model.Endereco; // Usaremos a entidade Endereco diretamente aqui.
                                          // Alternativamente, você poderia criar um EnderecoDTO.
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent; // Para data de nascimento

import java.time.LocalDate;

public class MedicoRequestDTO {

    // Campos da Pessoa
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    // Adicione @Pattern(regexp = "...") para validar o formato do CPF se desejar
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @PastOrPresent(message = "A data de nascimento não pode ser no futuro.")
    private LocalDate dataDeNascimento;

    @NotBlank(message = "O telefone de contato é obrigatório.")
    private String telefoneContato;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ter um formato válido.")
    @Size(max = 100, message = "O email não pode exceder 100 caracteres.")
    private String emailContato;

    @NotNull(message = "O endereço é obrigatório.")
    @Valid // Esta anotação é crucial para validar os campos dentro do objeto Endereco
    private Endereco endereco;

    // Campos específicos do Medico
    @NotBlank(message = "O CRM é obrigatório.")
    @Size(max = 20, message = "O CRM não pode exceder 20 caracteres.")
    private String crm;

    @NotBlank(message = "A especialidade é obrigatória.")
    @Size(max = 100, message = "A especialidade não pode exceder 100 caracteres.")
    private String especialidade;

    // Getters e Setters (necessários para o Spring/Jackson realizar o binding e a validação)

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}