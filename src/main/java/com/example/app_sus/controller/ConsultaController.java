package com.example.app_sus.controller;

import com.example.app_sus.dto.ConsultaRequestDTO;
import com.example.app_sus.dto.ConsultaResponseDTO;
import com.example.app_sus.model.Consulta;
import com.example.app_sus.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService /* MedicoService medicoService REMOVIDO */) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> criarConsulta(@Valid @RequestBody ConsultaRequestDTO consultaRequestDTO) {
        System.out.println("CONTROLLER: Método criarConsulta ACIONADO!"); // LOG NOVO
        try {
            System.out.println("CONTROLLER: Recebido ConsultaRequestDTO - MedicoID: " + consultaRequestDTO.getMedicoId() +
                            ", PacienteID: " + consultaRequestDTO.getPacienteId() +
                            ", DataHora: " + consultaRequestDTO.getDataHora()); // LOG NOVO
            Consulta novaConsulta = consultaService.criarConsulta(consultaRequestDTO);
            System.out.println("CONTROLLER: Consulta criada pelo service com ID: " + novaConsulta.getId()); // LOG NOVO
            return ResponseEntity.status(HttpStatus.CREATED).body(new ConsultaResponseDTO(novaConsulta));
        } catch (Exception e) {
            System.err.println("CONTROLLER ERROR: Exceção ao criar consulta: " + e.getMessage()); // LOG DE ERRO NOVO
            // e.printStackTrace(); // Descomente para ver o stack trace completo no console do backend
            // Deixe o GlobalExceptionHandler tratar o retorno HTTP, mas logamos aqui.
            throw e; // Relança para o GlobalExceptionHandler ou Spring tratar
        }
    }

    @GetMapping
    public List<ConsultaResponseDTO> listarTodasConsultas() {
        // medicoService NÃO é usado aqui
        return consultaService.listarTodas().stream()
                .map(ConsultaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarConsultaPorId(@PathVariable Long id) {
        // medicoService NÃO é usado aqui
        Consulta consulta = consultaService.buscarPorIdOuLancarExcecao(id);
        return ResponseEntity.ok(new ConsultaResponseDTO(consulta));
    }

    @GetMapping("/medico/{medicoId}")
    public List<ConsultaResponseDTO> listarConsultasPorMedico(@PathVariable Long medicoId) {
        // medicoService NÃO é usado aqui, pois consultaService.buscarConsultasPorMedico já recebe o medicoId
        return consultaService.buscarConsultasPorMedico(medicoId).stream()
                .map(ConsultaResponseDTO::new)
                .collect(Collectors.toList());
    }
}