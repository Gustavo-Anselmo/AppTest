package com.example.app_sus.model;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Auxiliar extends Pessoa {

    private String setor;

    public Auxiliar() {
        super();
    }

    public Auxiliar(String nome, String cpf, LocalDate dataDeNascimento, Endereco endereco,
                    String telefoneContato, String emailContato, String setor) {
        super(nome, cpf, dataDeNascimento, telefoneContato, emailContato, endereco);
        this.setor = setor;
    }


    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}