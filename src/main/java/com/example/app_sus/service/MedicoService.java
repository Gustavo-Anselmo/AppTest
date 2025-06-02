package com.example.app_sus.service;

import com.example.app_sus.dto.MedicoRequestDTO; // IMPORTADO
import com.example.app_sus.exception.RecursoNaoEncontradoException;
import com.example.app_sus.mapper.MedicoMapper; // IMPORTADO
import com.example.app_sus.model.Medico;
import com.example.app_sus.model.estado.MedicoDisponivel;
import com.example.app_sus.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final GerenciadorDeAgendaService gerenciadorDeAgendaService; // Observer
    private final MedicoMapper medicoMapper; // ADICIONADO: Injetar o Mapper

    @Autowired
    public MedicoService(MedicoRepository medicoRepository,
                         @Lazy GerenciadorDeAgendaService gerenciadorDeAgendaService,
                         MedicoMapper medicoMapper) { // ADICIONADO: MedicoMapper no construtor
        this.medicoRepository = medicoRepository;
        this.gerenciadorDeAgendaService = gerenciadorDeAgendaService;
        this.medicoMapper = medicoMapper; // ADICIONADO
    }

    @Transactional(readOnly = true)
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

@Transactional(readOnly = true)
public Medico buscarPorIdOuLancarExcecao(Long id) {
    Medico medico = medicoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado com o ID: " + id));
    
    // O construtor de Medico e o método getDescricaoEstadoAtual já garantem que 
    // 'estadoAtual' seja inicializado com MedicoDisponivel se for null.
    // A principal ação aqui é garantir que o observador seja registrado.
    medico.adicionarObservador(gerenciadorDeAgendaService); 
    
    return medico;
}
    
    // MÉTODO ATUALIZADO PARA USAR MedicoRequestDTO
    @Transactional
    public Medico criarMedico(MedicoRequestDTO medicoRequestDTO) {
        Medico medico = medicoMapper.toEntity(medicoRequestDTO);
        
        // O construtor de Medico já define o estadoAtual como MedicoDisponivel.
        // Se houver um campo de status persistido no Medico (ex: um Enum), 
        // ele seria setado aqui também antes de salvar, ou o estadoAtual seria 
        // instanciado com base nesse Enum.

        // Validações de negócio específicas (ex: verificar se CRM já existe) podem vir aqui.
        // Ex: if (medicoRepository.findByCrm(medico.getCrm()).isPresent()) {
        //         throw new IllegalArgumentException("CRM já cadastrado.");
        //     }

        Medico medicoSalvo = medicoRepository.save(medico);
        
        // Adiciona o observador ao médico recém-criado e salvo
        medicoSalvo.adicionarObservador(gerenciadorDeAgendaService);
        
        return medicoSalvo;
    }

    @Transactional
    public Medico colocarMedicoDeFerias(Long id) {
        Medico medico = buscarPorIdOuLancarExcecao(id); // Já adiciona observador aqui
        medico.solicitarFerias(); 
        // Se você tivesse um campo Enum para persistir o estado (ex: StatusMedicoPersistente):
        // medico.setStatusPersistido(StatusMedicoPersistente.FERIAS);
        return medicoRepository.save(medico); 
    }

    @Transactional
    public Medico colocarMedicoDeLicenca(Long id) {
        Medico medico = buscarPorIdOuLancarExcecao(id); // Já adiciona observador aqui
        medico.entrarEmLicenca(); 
        // medico.setStatusPersistido(StatusMedicoPersistente.LICENCA_MEDICA);
        return medicoRepository.save(medico);
    }

    @Transactional
    public Medico fazerMedicoRetornarAoTrabalho(Long id) {
        Medico medico = buscarPorIdOuLancarExcecao(id); // Já adiciona observador aqui
        medico.retornarAoTrabalho();
        // medico.setStatusPersistido(StatusMedicoPersistente.DISPONIVEL);
        return medicoRepository.save(medico);
    }
}