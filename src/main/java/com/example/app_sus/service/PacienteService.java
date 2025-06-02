package com.example.app_sus.service;

import com.example.app_sus.dto.PacienteRequestDTO; // IMPORTAR
import com.example.app_sus.exception.RecursoNaoEncontradoException; // IMPORTAR (se for usar)
import com.example.app_sus.mapper.PacienteMapper;   // IMPORTAR
import com.example.app_sus.model.Paciente;
import com.example.app_sus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired; // IMPORTAR
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // IMPORTAR

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper; // ADICIONAR PacienteMapper

    @Autowired // Adicionar @Autowired se não estiver usando injeção via construtor para todas as dependências
    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) { // ADICIONAR PacienteMapper
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper; // ADICIONAR
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    // Adicionar método buscarPorIdOuLancarExcecao para consistência, se desejar
    @Transactional(readOnly = true)
    public Paciente buscarPorIdOuLancarExcecao(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + id));
    }

    // MÉTODO SALVAR ATUALIZADO PARA RECEBER PacienteRequestDTO
    @Transactional
    public Paciente salvar(PacienteRequestDTO pacienteRequestDto) {
        // Validação extra: Se possuiDeficiencia=true, tipoDeDeficiencia não pode ser nulo.
        if (pacienteRequestDto.getPossuiDeficiencia() != null && pacienteRequestDto.getPossuiDeficiencia() && pacienteRequestDto.getTipoDeDeficiencia() == null) {
            throw new IllegalArgumentException("Tipo de deficiência é obrigatório quando o paciente possui deficiência.");
        }
        
        Paciente paciente = pacienteMapper.toEntity(pacienteRequestDto); // Usa o mapper
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void deletar(Long id) {
        if (!pacienteRepository.existsById(id)) { // Boa prática verificar antes de deletar
            throw new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + id + " para exclusão.");
        }
        pacienteRepository.deleteById(id);
    }
}