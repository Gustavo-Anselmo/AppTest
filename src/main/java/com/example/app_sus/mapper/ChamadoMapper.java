package com.example.app_sus.mapper;

import com.example.app_sus.dto.ChamadoRequestDTO;
import com.example.app_sus.model.Chamado;
import com.example.app_sus.model.Paciente;
import com.example.app_sus.model.Usuario; 
// Não precisamos importar Endereco aqui se o DTO e a Entidade usam o mesmo tipo Endereco

import org.springframework.stereotype.Component;

@Component
public class ChamadoMapper {

    public Chamado toEntity(ChamadoRequestDTO dto, Paciente paciente, Usuario usuario) {
        if (dto == null) {
            return null;
        }
        // O construtor padrão de Chamado já inicializa dataHoraChamado e status.
        Chamado chamado = new Chamado();
        
        chamado.setPaciente(paciente); // Paciente já buscado pelo Service
        chamado.setUsuario(usuario);   // Usuário (atendente) já buscado/definido pelo Service

        chamado.setSintomas(dto.getSintomas());
        chamado.setObservacoesAdicionais(dto.getObservacoesAdicionais());
        chamado.setEnderecoAtendimento(dto.getEnderecoAtendimento()); // Assume que Endereco do DTO pode ser usado

        return chamado;
    }
}