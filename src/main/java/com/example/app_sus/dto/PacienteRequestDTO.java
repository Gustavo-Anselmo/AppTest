package com.example.app_sus.dto;

import com.example.app_sus.model.Endereco; // Usaremos a entidade Endereco. Pode ser um EnderecoDTO.
import com.example.app_sus.model.enums.TipoDeDeficiencia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PacienteRequestDTO {

    // Campos da Pessoa
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    // Adicione @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido.") se quiser validar o formato
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
    @Valid // Para validar os campos dentro do objeto Endereco
    private Endereco endereco;

    // Campos específicos do Paciente
    @NotNull(message = "É necessário informar se o paciente possui deficiência (true/false).")
    private Boolean possuiDeficiencia; // Usar Boolean para permitir nulo se não for obrigatório no JSON inicial

    private TipoDeDeficiencia tipoDeDeficiencia; // Será validado no service: obrigatório se possuiDeficiencia = true

    private String observacoesDeficiencia;

    // Getters e Setters (Essenciais para o Spring/Jackson)

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

    public Boolean getPossuiDeficiencia() { // Getter para Boolean
        return possuiDeficiencia;
    }

    public void setPossuiDeficiencia(Boolean possuiDeficiencia) {
        this.possuiDeficiencia = possuiDeficiencia;
    }

    public TipoDeDeficiencia getTipoDeDeficiencia() {
        return tipoDeDeficiencia;
    }

    public void setTipoDeDeficiencia(TipoDeDeficiencia tipoDeDeficiencia) {
        this.tipoDeDeficiencia = tipoDeDeficiencia;
    }

    public String getObservacoesDeficiencia() {
        return observacoesDeficiencia;
    }

    public void setObservacoesDeficiencia(String observacoesDeficiencia) {
        this.observacoesDeficiencia = observacoesDeficiencia;
    }
}