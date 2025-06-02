package com.example.app_sus.config;

import com.example.app_sus.model.Auxiliar;
import com.example.app_sus.model.Endereco;
import com.example.app_sus.model.Usuario;
import com.example.app_sus.model.enums.Papel;
import com.example.app_sus.repository.AuxiliarRepository; // Importar
import com.example.app_sus.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    @Transactional
    public CommandLineRunner loadInitialData(
            UsuarioRepository usuarioRepository,
            AuxiliarRepository auxiliarRepository, // Adicionar
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Usuário Atendente Padrão
            if (usuarioRepository.findByUsername("atendente_teste").isEmpty()) {
                Auxiliar auxiliarPessoa = new Auxiliar();
                auxiliarPessoa.setNome("Atendente Padrão Inicial");
                auxiliarPessoa.setCpf("000.000.000-00"); // Use CPFs únicos se tiver constraint
                auxiliarPessoa.setDataDeNascimento(LocalDate.of(1990, 1, 1));
                auxiliarPessoa.setTelefoneContato("12345-6789");
                auxiliarPessoa.setEmailContato("atendente.inicial@sus.com");
                // Endereco será salvo por cascata de Pessoa se Pessoa.endereco tiver CascadeType.ALL
                auxiliarPessoa.setEndereco(new Endereco("Rua Principal Atendente", "789", "Sala 1", "Centro", "AppCidade", "AC", "70000-000", "Brasil"));
                auxiliarPessoa.setSetor("Atendimento Primário");

                System.out.println("DataLoader: Salvando auxiliarPessoa...");
                // Salva a Pessoa (Auxiliar) explicitamente ANTES de associar ao Usuario
                // Isso é uma alternativa se o cascade em Usuario.pessoa não estiver funcionando como esperado,
                // ou para garantir que auxiliarPessoa tenha um ID antes da associação.
                // Com CascadeType.ALL em Usuario.pessoa, este save explícito de auxiliarPessoa
                // pode não ser estritamente necessário se você salvar apenas o atendenteUser,
                // mas não fará mal se a Pessoa for nova. Se a Pessoa já existisse e você quisesse associá-la,
                // você a buscaria e não a salvaria novamente aqui.
                auxiliarRepository.save(auxiliarPessoa);
                System.out.println("DataLoader: auxiliarPessoa salva com ID: " + auxiliarPessoa.getId());


                Usuario atendenteUser = new Usuario();
                atendenteUser.setUsername("atendente_teste");
                atendenteUser.setSenha(passwordEncoder.encode("senha123"));
                atendenteUser.setPapel(Papel.AUXILIAR);
                atendenteUser.setAtivo(true);
                atendenteUser.setPessoa(auxiliarPessoa); // Associa a instância JÁ PERSISTIDA de Auxiliar

                System.out.println("DataLoader: Tentando salvar atendenteUser...");
                usuarioRepository.save(atendenteUser);
                System.out.println("Usuário 'atendente_teste' criado com senha 'senha123'");
            } else {
                System.out.println("Usuário 'atendente_teste' já existe.");
            }

            // Remova ou comente a criação dos usuários "Medico" e "Paciente" por enquanto
            // para simplificar e garantir que a aplicação suba.
            // Poderemos readicioná-los depois que a aplicação estiver estável.
        };
    }
}