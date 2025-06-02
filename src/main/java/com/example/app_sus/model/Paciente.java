package com.example.app_sus.model;

import com.example.app_sus.model.enums.TipoDeDeficiencia; // Certifique-se que este import está correto
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull; // Para a anotação na entidade, se aplicável

import java.time.LocalDate;

@Entity
public class Paciente extends Pessoa {

    @NotNull(message= "É necessário informar se o paciente possui deficiência (true/false).")
    private boolean possuiDeficiencia; // boolean primitivo é uma boa escolha aqui

    @Enumerated(EnumType.STRING)
    private TipoDeDeficiencia tipoDeDeficiencia; // O tipo do enum

    private String observacoesDeficiencia;

    public Paciente() {
        super(); // Chama o construtor da classe Pessoa
    }

    public Paciente(String nome, String cpf, LocalDate dataDeNascimento, String telefoneContato, String emailContato,
                    Endereco endereco, boolean possuiDeficiencia, TipoDeDeficiencia tipoDeDeficiencia, String observacoesDeficiencia) {
        super(nome, cpf, dataDeNascimento, telefoneContato, emailContato, endereco);
        this.possuiDeficiencia = possuiDeficiencia;
        this.tipoDeDeficiencia = tipoDeDeficiencia;
        this.observacoesDeficiencia = observacoesDeficiencia;
    }

    // Getters e Setters

    // Para boolean, o getter convencional é "is" mas "get" também funciona e é usado pelo Jackson.
    // Manterei getPossuiDeficiencia como você tinha, mas isPossuiDeficiencia é mais comum.
    public boolean getPossuiDeficiencia() {
        return possuiDeficiencia;
    }

    public void setPossuiDeficiencia(boolean possuiDeficiencia) {
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

    // Método herdado de Pessoa (se você o implementou lá para polimorfismo)
    // Se Pessoa não tiver um método abstrato getTipoPessoa(), remova este @Override.
    // Se Pessoa tiver, certifique-se que a assinatura bate.
    // @Override
    // public String getTipoPessoa() {
    //     return "Paciente";
    // }
}