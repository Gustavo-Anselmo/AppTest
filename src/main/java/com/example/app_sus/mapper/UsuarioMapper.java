package com.example.app_sus.mapper;

import com.example.app_sus.dto.UsuarioRequestDTO;
import com.example.app_sus.model.Pessoa; // Precisamos instanciar Pessoa
import com.example.app_sus.model.Usuario;
// Se o papel MEDICO ou AUXILIAR for selecionado, precisaremos instanciar Medico ou Auxiliar
import com.example.app_sus.model.Medico; 
import com.example.app_sus.model.Auxiliar;
import com.example.app_sus.model.enums.Papel;

import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Pessoa pessoaAssociada;
        // Decide qual tipo de Pessoa criar baseado no Papel
        if (dto.getPapel() == Papel.MEDICO) {
            // Se Médico tiver campos específicos no DTO, adicione-os aqui
            // Por enquanto, criamos um Médico com os dados de Pessoa
            Medico medico = new Medico();
            // Se MedicoRequestDTO tivesse crm e especialidade, seriam setados aqui
            // medico.setCrm(dto.getCrmMedico()); 
            // medico.setEspecialidade(dto.getEspecialidadeMedico());
            pessoaAssociada = medico;
        } else if (dto.getPapel() == Papel.AUXILIAR) {
            // Se Auxiliar tiver campos específicos no DTO, adicione-os aqui
            Auxiliar auxiliar = new Auxiliar();
            // auxiliar.setSetor(dto.getSetorAuxiliar());
            pessoaAssociada = auxiliar;
        } else {
            // Para ADMIN ou outros papéis que não são subtipos específicos de Pessoa
            // ou se você não quer criar subtipos específicos aqui, pode usar uma Pessoa genérica
            // (mas Pessoa é abstrata, então isso não funcionaria diretamente).
            // Vamos assumir que ADMIN pode ser uma Pessoa sem especialização,
            // mas como Pessoa é abstrata, isso é um problema.
            // SOLUÇÃO: Para ADMIN, talvez não crie uma Pessoa separada ou tenha uma lógica diferente.
            // Por simplicidade, se não for Medico ou Auxiliar, vamos lançar um erro por enquanto,
            // ou você pode definir um tipo de Pessoa padrão se fizer sentido.
            // OU, se Admin não precisa de dados de Pessoa, o relacionamento em Usuario seria opcional.
            // Visto que Usuario TEM um relacionamento com Pessoa, precisamos de uma instância de Pessoa.
            // Se o seu Admin não tem setor ou crm, mas ainda é uma Pessoa:
            // Você precisaria de uma classe concreta 'PessoaGenerica' que estende Pessoa,
            // ou fazer com que Medico/Auxiliar sejam os únicos tipos de Pessoa que podem ser Usuários.
            // Vamos simplificar e assumir que o DTO sempre corresponde a um papel que cria uma Pessoa concreta.
            // Se papel for ADMIN, e Admin não tem dados além de Pessoa, precisamos de um subtipo.
            // Alternativa: se Papel.ADMIN não implica uma Pessoa específica, ajuste a lógica.
            // Para este exemplo, vamos focar em Medico e Auxiliar para criar a Pessoa.
            // Se for ADMIN, o campo pessoa no Usuario pode ficar nulo se o JoinColumn permitir,
            // ou você precisa de uma estratégia.
            // Por agora, vamos assumir que Medico ou Auxiliar são os mais comuns para cadastro com Pessoa.
            // Se o papel for ADMIN e não houver dados de Pessoa no DTO, trate aqui.
            // **ESTA LÓGICA DE PESSOA PRECISA SER REFINADA CONFORME SEU MODELO DE NEGÓCIO**
            // Se um Usuario ADMIN não tem uma Pessoa associada, o relacionamento @OneToOne em Usuario
            // com Pessoa deveria ter (optional = true) ou nullable = true no @JoinColumn.
            // Para o exemplo, vamos criar um Medico se o papel for MEDICO, Auxiliar se for AUXILIAR.
            // Se for ADMIN, e Pessoa é obrigatória, você precisa definir como criar essa Pessoa.
            // **Simplificação drástica por agora: Pessoa será criada, mas sem subtipo se não for Medico/Auxiliar**
            // ESTA É UMA LIMITAÇÃO DO EXEMPLO ATUAL DEVIDO A PESSOA SER ABSTRATA.
            // Você precisaria de uma implementação concreta de Pessoa ou ajustar a criação.
            // Como temos Medico e Auxiliar que estendem Pessoa:
            if (dto.getPapel() == Papel.MEDICO) {
                pessoaAssociada = new Medico(); 
            } else if (dto.getPapel() == Papel.AUXILIAR) {
                pessoaAssociada = new Auxiliar();
            } else {
                // O que fazer para ADMIN? Pessoa é abstrata.
                // Você precisaria de uma entidade concreta, ex: Funcionario extends Pessoa
                // ou não associar Pessoa para ADMIN se o relacionamento permitir.
                // Por agora, vamos lançar uma exceção se não for Medico ou Auxiliar para ser explícito.
                 throw new IllegalArgumentException("Criação de Pessoa para o papel " + dto.getPapel() + " não suportada diretamente neste mapper simples. Defina um subtipo concreto de Pessoa.");
            }
        }

        pessoaAssociada.setNome(dto.getNomePessoa());
        pessoaAssociada.setCpf(dto.getCpfPessoa());
        pessoaAssociada.setDataDeNascimento(dto.getDataDeNascimentoPessoa());
        pessoaAssociada.setTelefoneContato(dto.getTelefoneContatoPessoa());
        pessoaAssociada.setEmailContato(dto.getEmailContatoPessoa());
        pessoaAssociada.setEndereco(dto.getEnderecoPessoa()); // Endereco é um objeto

        // Se for Medico, setar CRM e Especialidade (se viessem do DTO)
        if (pessoaAssociada instanceof Medico && dto.getPapel() == Papel.MEDICO) {
            // ((Medico) pessoaAssociada).setCrm(dto.getCrmMedico());
            // ((Medico) pessoaAssociada).setEspecialidade(dto.getEspecialidadeMedico());
            // Para isso, MedicoRequestDTO precisaria ter crmMedico e especialidadeMedico
        }
        // Se for Auxiliar, setar Setor (se viesse do DTO)
        if (pessoaAssociada instanceof Auxiliar && dto.getPapel() == Papel.AUXILIAR) {
            // ((Auxiliar) pessoaAssociada).setSetor(dto.getSetorAuxiliar());
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setSenha(dto.getSenha()); // A senha será criptografada no Service
        usuario.setPapel(dto.getPapel());
        usuario.setAtivo(true); // Usuários são criados como ativos por padrão
        usuario.setPessoa(pessoaAssociada);

        return usuario;
    }
}