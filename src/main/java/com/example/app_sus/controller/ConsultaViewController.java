// Em seu ConsultaViewController.java (crie se não existir)
package com.example.app_sus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultaViewController {

    @GetMapping("/consultas/cadastro")
    public String agendarConsultaForm() {
        return "consulta-form";
    }

    @GetMapping("/consultas/lista")
    public String listarConsultasPage() {
        return "listar-consultas";
    }

}