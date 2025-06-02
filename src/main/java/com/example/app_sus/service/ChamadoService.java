package com.example.app_sus.service;

import com.example.app_sus.dto.ChamadoRequestDTO; // IMPORTAR
import com.example.app_sus.exception.RecursoNaoEncontradoException;
import com.example.app_sus.mapper.ChamadoMapper; // IMPORTAR
import com.example.app_sus.model.Chamado;
import com.example.app_sus.model.Paciente;
import com.example.app_sus.model.Usuario;
import com.example.app_sus.model.enums.StatusChamado;
import com.example.app_sus.repository.ChamadoRepository;
import com.example.app_sus.repository.PacienteRepository;
import com.example.app_sus.repository.UsuarioRepository; // Necessário se você buscar o usuário aqui

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository; // Para buscar o usuário se necessário
    private final ChamadoMapper chamadoMapper;         // IMPORTAR e INJETAR

    @Autowired
    public ChamadoService(ChamadoRepository chamadoRepository,
                          PacienteRepository pacienteRepository,
                          UsuarioRepository usuarioRepository,
                          ChamadoMapper chamadoMapper) { // Adicionar mapper
        this.chamadoRepository = chamadoRepository;
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.chamadoMapper = chamadoMapper; // Atribuir
    }

    // ... listarTodos, buscarPorId, buscarPorIdOuLancarExcecao ... permanecem os mesmos

    @Transactional
    public Chamado salvar(ChamadoRequestDTO chamadoRequestDto, Usuario usuarioLogado) { // Recebe DTO e Usuario logado
        Paciente pacienteExistente = pacienteRepository.findById(chamadoRequestDto.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + chamadoRequestDto.getPacienteId()));

        // O usuarioLogado é quem está registrando o chamado.
        // Em um sistema real, ele viria do Spring Security.
        if (usuarioLogado == null) {
            // Lógica para lidar com usuário não autenticado ou buscar um usuário padrão para teste
            // Por agora, vamos lançar um erro se não for fornecido (o controller DEVE passar)
            throw new IllegalStateException("Usuário (atendente) não fornecido para registrar o chamado.");
        }
        // Se o usuarioLogado já é uma entidade gerenciada, ótimo. 
        // Se for apenas um objeto com ID, você precisaria buscá-lo:
        // Usuario atendente = usuarioRepository.findById(usuarioLogado.getId()).orElseThrow(...);

        Chamado chamado = chamadoMapper.toEntity(chamadoRequestDto, pacienteExistente, usuarioLogado);
        
        // Status e dataHoraChamado são definidos no construtor padrão de Chamado.
        // Se precisar sobrescrever ou se não forem definidos lá, faça aqui:
        if (chamado.getStatus() == null) {
            chamado.setStatus(StatusChamado.NOVO);
        }
        if (chamado.getDataHoraChamado() == null) {
            chamado.setDataHoraChamado(LocalDateTime.now());
        }

        return chamadoRepository.save(chamado);
    }
    
    // Manter os outros métodos (listarTodos, buscarPorId, deletar) como estavam,
    // mas o buscarPorId no controller precisará de um ChamadoResponseDTO
    @Transactional(readOnly = true)
    public List<Chamado> listarTodos() {
        return chamadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Chamado> buscarPorId(Long id) {
        return chamadoRepository.findById(id);
    }
     @Transactional(readOnly = true)
    public Chamado buscarPorIdOuLancarExcecao(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado não encontrado com ID: " + id));
    }


    @Transactional
    public void deletar(Long id) {
        if (!chamadoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Chamado não encontrado com o ID: " + id + " para exclusão.");
        }
        chamadoRepository.deleteById(id);
    }
}