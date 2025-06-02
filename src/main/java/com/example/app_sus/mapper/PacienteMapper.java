package com.example.app_sus.mapper;

import com.example.app_sus.dto.PacienteRequestDTO;
import com.example.app_sus.model.Paciente;
// Se você usar um EnderecoDTO dentro do PacienteRequestDTO, você precisaria de um EnderecoMapper também.
// Por enquanto, estamos assumindo que PacienteRequestDTO tem um campo do tipo Endereco (entidade).
// import com.example.app_sus.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Paciente paciente = new Paciente();

        // Mapeando campos da superclasse Pessoa
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataDeNascimento(dto.getDataDeNascimento());
        paciente.setTelefoneContato(dto.getTelefoneContato());
        paciente.setEmailContato(dto.getEmailContato());
        paciente.setEndereco(dto.getEndereco()); // Assume que o DTO tem um objeto Endereco

        // Mapeando campos específicos de Paciente
        boolean possuiDeficiencia = dto.getPossuiDeficiencia() != null && dto.getPossuiDeficiencia();
        paciente.setPossuiDeficiencia(possuiDeficiencia);

        if (possuiDeficiencia) {
            paciente.setTipoDeDeficiencia(dto.getTipoDeDeficiencia()); // Chama o setter correto
            paciente.setObservacoesDeficiencia(dto.getObservacoesDeficiencia());
        } else {
            // Garante que, se não possui deficiência, os campos relacionados fiquem nulos/vazios
            paciente.setTipoDeDeficiencia(null);
            paciente.setObservacoesDeficiencia(null);
        }

        return paciente;
    }

    // Se você precisar de um método para converter Entidade para ResponseDTO:
    // public PacienteResponseDTO toResponseDTO(Paciente paciente) {
    //     if (paciente == null) {
    //         return null;
    //     }
    //     // Assumindo que PacienteResponseDTO tem um construtor que aceita Paciente
    //     return new PacienteResponseDTO(paciente); 
    // }
}