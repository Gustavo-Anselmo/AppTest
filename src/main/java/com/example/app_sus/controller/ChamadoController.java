package com.example.app_sus.controller;

import com.example.app_sus.dto.ChamadoRequestDTO;
import com.example.app_sus.dto.ChamadoResponseDTO;
import com.example.app_sus.model.Chamado;
import com.example.app_sus.model.Usuario;
import com.example.app_sus.service.ChamadoService;
import com.example.app_sus.service.UsuarioService;
import com.example.app_sus.exception.RecursoNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chamados") // CORRETO
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final UsuarioService usuarioService;

    public ChamadoController(ChamadoService chamadoService, UsuarioService usuarioService) {
        this.chamadoService = chamadoService;
        this.usuarioService = usuarioService;
    }

    // Em ChamadoController.java
    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> salvar(@Valid @RequestBody ChamadoRequestDTO chamadoRequestDto) {
        System.out.println("CONTROLLER: Método salvar chamado ACIONADO! DTO recebido.");
        System.out.println("CONTROLLER: Recebido PacienteID: " + chamadoRequestDto.getPacienteId() + 
                        ", UsuarioID: " + chamadoRequestDto.getUsuarioId()); // Log do UsuarioID

        // Busca o usuário com base no ID fornecido no DTO
        Usuario usuarioLogado = usuarioService.buscarPorId(chamadoRequestDto.getUsuarioId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário (atendente) com ID " + 
                                chamadoRequestDto.getUsuarioId() + " não encontrado."));
        System.out.println("CONTROLLER: Usuário " + usuarioLogado.getUsername() + " obtido para o chamado.");

        Chamado chamadoSalvo = chamadoService.salvar(chamadoRequestDto, usuarioLogado);
        System.out.println("CONTROLLER: Chamado salvo com ID: " + chamadoSalvo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ChamadoResponseDTO(chamadoSalvo));
    }

    // ... outros métodos GET, DELETE etc. ...
    @GetMapping
    public List<ChamadoResponseDTO> listarTodos() {
        return chamadoService.listarTodos().stream()
                .map(ChamadoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<ChamadoResponseDTO> buscarPorId(@PathVariable Long id) {
        Chamado chamado = chamadoService.buscarPorIdOuLancarExcecao(id);
        return ResponseEntity.ok(new ChamadoResponseDTO(chamado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        chamadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}