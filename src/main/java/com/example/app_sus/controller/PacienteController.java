package com.example.app_sus.controller;

import com.example.app_sus.dto.PacienteRequestDTO;  // Você precisará criar este DTO
import com.example.app_sus.dto.PacienteResponseDTO;
import com.example.app_sus.model.Paciente;
import com.example.app_sus.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes") // Caminho base para a API de pacientes
public class PacienteController {

    private final PacienteService pacienteService;
    // Se você criar um PacienteMapper, injete-o aqui também
    // private final PacienteMapper pacienteMapper;

    @Autowired
    public PacienteController(PacienteService pacienteService /*, PacienteMapper pacienteMapper */) {
        this.pacienteService = pacienteService;
        // this.pacienteMapper = pacienteMapper;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvar(
            @Valid @RequestBody PacienteRequestDTO pacienteRequestDto) { // Recebe o DTO de requisição
        
        // Aqui você precisaria converter PacienteRequestDTO para a entidade Paciente.
        // Isso pode ser feito com um PacienteMapper ou diretamente no service.
        // Exemplo (assumindo que o service fará o mapeamento ou receberá o DTO):
        Paciente pacienteSalvo = pacienteService.salvar(pacienteRequestDto); // O service precisará ser ajustado

        return ResponseEntity.status(HttpStatus.CREATED).body(new PacienteResponseDTO(pacienteSalvo));
    }

    @GetMapping
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteService.listarTodos().stream()
                .map(PacienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        // Idealmente, seu PacienteService teria um método que lança exceção se não encontrar
        // Paciente paciente = pacienteService.buscarPorIdOuLancarExcecao(id);
        // return ResponseEntity.ok(new PacienteResponseDTO(paciente));

        // Usando o método atual que retorna Optional:
        return pacienteService.buscarPorId(id)
                .map(paciente -> ResponseEntity.ok(new PacienteResponseDTO(paciente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        // É uma boa prática verificar se o recurso existe antes de deletar
        if (pacienteService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Se você tiver um endpoint PUT para atualização, ele também usaria DTOs:
    /*
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, 
                                                        @Valid @RequestBody PacienteRequestDTO pacienteRequestDto) {
        Paciente pacienteAtualizado = pacienteService.atualizar(id, pacienteRequestDto); // Service precisa deste método
        return ResponseEntity.ok(new PacienteResponseDTO(pacienteAtualizado));
    }
    */
}