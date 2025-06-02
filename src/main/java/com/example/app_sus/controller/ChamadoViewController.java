package com.example.app_sus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Adicione esta importação

@Controller
public class ChamadoViewController {

    @GetMapping("/chamados/lista")
    public String listarChamados() {
        return "listar-chamados"; // Retorna o nome do arquivo HTML sem a extensão
    }

    @GetMapping("/chamados/cadastro")
    public String exibirFormularioChamado() {
        return "chamado-form"; // Retorna o nome do arquivo HTML do formulário
    }

    // Você pode adicionar um método para a página de detalhes do chamado no futuro
    @GetMapping("/chamados/{id}/detalhes")
    public String detalhesChamado(@PathVariable Long id) {
        // No futuro, você pode adicionar um Model para passar dados para a página
        return "detalhes-chamado"; // Esta página ainda não existe, mas será o próximo passo
    }

}