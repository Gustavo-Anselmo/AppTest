package com.example.app_sus.model;

import com.example.app_sus.model.estado.EstadoMedico;
import com.example.app_sus.model.estado.MedicoDisponivel;
import com.example.app_sus.model.estado.MedicoDeLicenca; // Necessário para a verificação no setEstadoAtual
import com.example.app_sus.observer.MedicoObservador;
import com.example.app_sus.observer.MedicoObservavel;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient; // Para não persistir o objeto de estado diretamente
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Medico extends Pessoa implements MedicoObservavel { // Implementa MedicoObservavel

    private String crm;
    private String especialidade;

    @Transient // O objeto de estado em si não será uma coluna no banco.
               // Para persistir o estado, você normalmente teria um Enum `StatusMedico`
               // mapeado para uma coluna, e recriaria o objeto EstadoMedico correto ao carregar.
    private EstadoMedico estadoAtual;

    @Transient // A lista de observadores também não é persistida.
    private List<MedicoObservador> observadores = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultasAgendadas = new ArrayList<>();

    public Medico() {
        super();
        this.estadoAtual = new MedicoDisponivel(); // Estado inicial padrão
    }

    public Medico(String nome, String cpf, LocalDate dataDeNascimento, String telefoneContato,
                  String emailContato, Endereco endereco, String crm, String especialidade) {
        super(nome, cpf, dataDeNascimento, telefoneContato, emailContato, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
        this.estadoAtual = new MedicoDisponivel(); // Estado inicial padrão
    }

    // Métodos que delegam para o objeto de Estado (Padrão State)
    public void solicitarFerias() {
        this.estadoAtual.solicitarFerias(this);
    }

    public void entrarEmLicenca() {
        // Antes de mudar o estado, podemos garantir que os observadores já estejam registrados,
        // se o registro for feito dinamicamente. No nosso exemplo, o MedicoService cuidaria disso.
        this.estadoAtual.entrarEmLicenca(this);
    }

    public void retornarAoTrabalho() {
        this.estadoAtual.retornarAoTrabalho(this);
    }

    public boolean podeAgendarConsulta() {
        return this.estadoAtual.podeAgendarConsulta(this);
    }

    public String getDescricaoEstadoAtual() {
        if (this.estadoAtual == null) { // Salvaguarda se o estado não for inicializado
            this.estadoAtual = new MedicoDisponivel();
        }
        return this.estadoAtual.getDescricaoEstado();
    }

    // Método central para mudar o estado do Médico
    // Este método é chamado pelas implementações de EstadoMedico
    public void setEstadoAtual(EstadoMedico novoEstado) {
        System.out.println("MÉDICO (" + this.getNome() + "): Mudando estado de '" + 
                           (this.estadoAtual != null ? this.estadoAtual.getDescricaoEstado() : "N/A") + 
                           "' para '" + novoEstado.getDescricaoEstado() + "'.");
        this.estadoAtual = novoEstado;

        // Notifica os observadores SE o novo estado for MedicoDeLicenca (Padrão Observer)
        if (novoEstado instanceof MedicoDeLicenca) {
            notificarObservadores();
        }
    }

    // Implementação dos métodos da interface MedicoObservavel (Padrão Observer)
    @Override
    public void adicionarObservador(MedicoObservador observador) {
        if (!this.observadores.contains(observador)) {
            this.observadores.add(observador);
            System.out.println("MÉDICO (" + this.getNome() + "): Observador " + observador.getClass().getSimpleName() + " adicionado.");
        }
    }

    @Override
    public void removerObservador(MedicoObservador observador) {
        this.observadores.remove(observador);
        System.out.println("MÉDICO (" + this.getNome() + "): Observador " + observador.getClass().getSimpleName() + " removido.");
    }

    @Override
    public void notificarObservadores() {
        System.out.println("MÉDICO (" + this.getNome() + "): Notificando " + this.observadores.size() + " observador(es) sobre mudança de estado para Licença Médica.");
        // Cria uma cópia da lista para evitar ConcurrentModificationException se um observador tentar se remover durante a notificação
        List<MedicoObservador> observadoresAtuais = new ArrayList<>(this.observadores);
        for (MedicoObservador observador : observadoresAtuais) {
            observador.atualizar(this, this.estadoAtual);
        }
    }

    // Getters e Setters para os atributos da classe Medico
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Consulta> getConsultasAgendadas() {
        return consultasAgendadas;
    }

    public void setConsultasAgendadas(List<Consulta> consultasAgendadas) {
        this.consultasAgendadas = consultasAgendadas;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultasAgendadas.add(consulta);
        consulta.setMedico(this); // Garante a bidirecionalidade da relação
    }
}