package com.example.app_sus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PacienteViewController {

    @GetMapping("/")
    public String homePage() {
        return "index"; // Ou a página que você quer como home após login
    }

    @GetMapping("/pacientes/cadastro")
    public String showCadastroPacienteForm() {
        return "index";
    }

    @GetMapping("/pacientes/lista")
    public String listarPacientes() {
        return "listar-pacientes";
    }

    @GetMapping("/pacientes/{id}/detalhes")
    public String detalhesPaciente(@PathVariable Long id) {
        return "paciente-detalhes";
    }

    @GetMapping("/login")
    public String paginaDeLogin() {
        return "login"; // Nome do seu template login.html
    }
}