package com.example.app_sus.service;

import com.example.app_sus.dto.ConsultaRequestDTO;
import com.example.app_sus.exception.RecursoNaoEncontradoException; // VERIFIQUE ESTE IMPORT
import com.example.app_sus.model.Consulta;
import com.example.app_sus.model.Medico;
import com.example.app_sus.model.Paciente;
import com.example.app_sus.model.enums.StatusConsulta;
import com.example.app_sus.repository.ConsultaRepository;
import com.example.app_sus.repository.MedicoRepository;   // VERIFIQUE ESTE IMPORT
import com.example.app_sus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;         // O tipo deve ser reconhecido agora
    private final PacienteRepository pacienteRepository;
    private final MedicoService medicoService;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                           MedicoRepository medicoRepository, // O tipo deve ser reconhecido agora
                           PacienteRepository pacienteRepository,
                           MedicoService medicoService) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoService = medicoService;
    }

@Transactional
public Consulta criarConsulta(ConsultaRequestDTO dto) {
    System.out.println("SERVICE: Iniciando criarConsulta com DTO: " + dto.getMedicoId() + ", " + dto.getPacienteId() + ", " + dto.getDataHora()); // LOG

    Medico medico = medicoService.buscarPorIdOuLancarExcecao(dto.getMedicoId());
    System.out.println("SERVICE: Médico encontrado: " + medico.getNome()); // LOG

    if (!medico.podeAgendarConsulta()) {
        System.err.println("SERVICE ERROR: Médico não pode agendar. Estado: " + medico.getDescricaoEstadoAtual()); // LOG DE ERRO
        throw new IllegalStateException("Médico " + medico.getNome() + " não está disponível para agendar consultas no momento ("+ medico.getDescricaoEstadoAtual() +").");
    }
    System.out.println("SERVICE: Médico pode agendar."); // LOG

    Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
            .orElseThrow(() -> {
                System.err.println("SERVICE ERROR: Paciente não encontrado com ID: " + dto.getPacienteId()); // LOG DE ERRO
                return new RecursoNaoEncontradoException("Paciente não encontrado com ID: " + dto.getPacienteId());
            });
    System.out.println("SERVICE: Paciente encontrado: " + paciente.getNome()); // LOG

    Consulta consulta = new Consulta();
    consulta.setMedico(medico);
    consulta.setPaciente(paciente);
    consulta.setDataHora(dto.getDataHora());
    consulta.setDescricao(dto.getDescricao());
    consulta.setStatusConsulta(StatusConsulta.AGENDADA);
    System.out.println("SERVICE: Objeto Consulta montado: " + consulta.getDescricao()); // LOG

    try {
        Consulta consultaSalva = consultaRepository.save(consulta);
        System.out.println("SERVICE: Consulta SALVA com ID: " + consultaSalva.getId()); // LOG
        return consultaSalva;
    } catch (Exception e) {
        System.err.println("SERVICE ERROR: Erro ao salvar consulta no repositório: " + e.getMessage()); // LOG DE ERRO
        // e.printStackTrace(); // Para ver o stack trace completo no console do backend
        throw e; // Relança a exceção para o Spring tratar (e possivelmente fazer rollback)
    }
}

    @Transactional(readOnly = true)
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarConsultasPorMedico(Long medicoId) {
        // Em vez de buscar o médico aqui e passar para o repository,
        // você pode criar um método no MedicoRepository para buscar por ID
        // ou passar o ID diretamente para um método customizado no ConsultaRepository.
        // Por agora, vamos assumir que o MedicoService já trata a busca do médico.
        Medico medico = medicoService.buscarPorIdOuLancarExcecao(medicoId);
        return consultaRepository.findByMedicoAndStatusConsulta(medico, StatusConsulta.AGENDADA);
    }
    
    @Transactional(readOnly = true)
    public List<Consulta> buscarTodasConsultasPorMedico(Medico medico) { 
        return consultaRepository.findByMedicoAndStatusConsulta(medico, StatusConsulta.AGENDADA);
    }

    @Transactional(readOnly = true)
    public Consulta buscarPorIdOuLancarExcecao(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada com ID: " + id)); // CORREÇÃO AQUI
    }

    @Transactional
    public Consulta salvar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }
}