package com.example.app_sus.controller;

import com.example.app_sus.dto.MedicoRequestDTO; // Importar
import com.example.app_sus.dto.MedicoResponseDTO;
import com.example.app_sus.model.Medico;
import com.example.app_sus.service.MedicoService;
import jakarta.validation.Valid; // Importar para validação
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importar para o status CREATED
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos") // Mantém o prefixo /api
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    // ENDPOINT ATUALIZADO/ADICIONADO PARA CRIAR MÉDICO
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criarMedico(@Valid @RequestBody MedicoRequestDTO medicoRequestDTO) {
        Medico novoMedico = medicoService.criarMedico(medicoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MedicoResponseDTO(novoMedico));
    }

    @GetMapping
    public List<MedicoResponseDTO> listarTodosMedicos() {
        return medicoService.listarTodos().stream()
                .map(MedicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<MedicoResponseDTO> buscarMedicoPorId(@PathVariable Long id) {
        Medico medico = medicoService.buscarPorIdOuLancarExcecao(id);
        return ResponseEntity.ok(new MedicoResponseDTO(medico));
    }

    @PostMapping("/{id}/solicitar-ferias")
    public ResponseEntity<MedicoResponseDTO> solicitarFerias(@PathVariable Long id) {
        Medico medicoAtualizado = medicoService.colocarMedicoDeFerias(id);
        return ResponseEntity.ok(new MedicoResponseDTO(medicoAtualizado));
    }

    @PostMapping("/{id}/entrar-em-licenca")
    public ResponseEntity<MedicoResponseDTO> entrarEmLicenca(@PathVariable Long id) {
        Medico medicoAtualizado = medicoService.colocarMedicoDeLicenca(id);
        return ResponseEntity.ok(new MedicoResponseDTO(medicoAtualizado));
    }

    @PostMapping("/{id}/retornar-ao-trabalho")
    public ResponseEntity<MedicoResponseDTO> retornarAoTrabalho(@PathVariable Long id) {
        Medico medicoAtualizado = medicoService.fazerMedicoRetornarAoTrabalho(id);
        return ResponseEntity.ok(new MedicoResponseDTO(medicoAtualizado));
    }
}