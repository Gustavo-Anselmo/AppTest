package com.example.app_sus.mapper;

import com.example.app_sus.dto.MedicoRequestDTO;
import com.example.app_sus.model.Medico;
// A entidade Endereco é usada diretamente no DTO e na entidade Medico,
// então não precisamos de um import específico para um EnderecoDTO aqui,
// a menos que você decida usar um EnderecoDTO dentro do MedicoRequestDTO.
// import com.example.app_sus.model.Endereco; 
import org.springframework.stereotype.Component;

@Component // Para que o Spring possa gerenciá-lo e injetá-lo no MedicoService
public class MedicoMapper {

    public Medico toEntity(MedicoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        // O construtor padrão de Medico já inicializa o estadoAtual como MedicoDisponivel
        // e a lista de observadores e consultas como ArrayLists vazias.
        Medico medico = new Medico();

        // Atributos da classe Pessoa
        medico.setNome(dto.getNome());
        medico.setCpf(dto.getCpf());
        medico.setDataDeNascimento(dto.getDataDeNascimento());
        medico.setTelefoneContato(dto.getTelefoneContato());
        medico.setEmailContato(dto.getEmailContato());
        
        // O Endereco é passado como um objeto diretamente do DTO para a entidade.
        // Se você tivesse um EnderecoDTO, a conversão seria feita aqui.
        // Ex: medico.setEndereco(enderecoMapper.toEntity(dto.getEnderecoDto()));
        medico.setEndereco(dto.getEndereco()); 

        // Atributos específicos do Medico
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        
        // O estado inicial (MedicoDisponivel) já é definido no construtor de Medico.
        // Se o DTO precisasse influenciar o estado inicial, a lógica seria aqui.

        return medico;
    }

    // Se você precisar converter Medico (entidade) para MedicoResponseDTO,
    // você poderia adicionar um método aqui também, embora já tenhamos feito
    // isso no construtor do MedicoResponseDTO.
    // Exemplo:
    // public MedicoResponseDTO toResponseDTO(Medico medico) {
    //     if (medico == null) {
    //         return null;
    //     }
    //     return new MedicoResponseDTO(medico);
    // }
}