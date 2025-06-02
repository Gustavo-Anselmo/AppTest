package com.example.app_sus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MedicoViewController {

    @GetMapping("/medicos/lista")
    public String listarMedicosPage() {
        return "listar-medicos"; // Serve a página listar-medicos.html
    }

    @GetMapping("/medicos/{id}/detalhes")
    public String detalhesMedicoPage(@PathVariable Long id) {
        // O JavaScript na página medico-detalhes.html buscará os dados do médico via API.
        // Não precisamos passar o objeto Medico aqui via Model, a menos que você queira
        // pré-carregar algo ou passar o ID para o JavaScript de uma forma diferente.
        return "medico-detalhes"; // Serve a página medico-detalhes.html
    }

    // NOVO MÉTODO ADICIONADO:
    @GetMapping("/medicos/formulario")
    public String cadastrarMedicoForm() {
        return "medico-form"; // Serve a página medico-form.html
    }

}