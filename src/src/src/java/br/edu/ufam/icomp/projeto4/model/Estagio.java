package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bruna
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Estagio.findByAlunoPeriodo", query = "SELECT e FROM Estagio e WHERE e.aluno.id = :idAluno AND e.periodo.id = :idPeriodo"),
    @NamedQuery(name = "Estagio.findByStatusAluno", query = "SELECT e FROM Estagio e WHERE e.aluno.id = :idAluno AND e.status = :status"),
    @NamedQuery(name = "Estagio.listByAluno", query = "SELECT e FROM Estagio e WHERE e.aluno.id = :id"),
    @NamedQuery(name="Estagio.findByStatusAndCursoAndPeriodo", query="SELECT e FROM Estagio e WHERE e.status = :status AND e.aluno.curso.id = :idCurso AND e.periodo.id = :idPeriodo")
        
})
public class Estagio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_estagio", sequenceName = "estagio_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_estagio", strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String termoCompromisso;
    @Column(nullable = false)
    @NotNull(message = "Desculpe! O campo nome da empresa é obrigatório")
    private String empresa;
    @Column(nullable = true)
    private String fichaCadastroEmpresa;
    @Column(nullable = false)
    private boolean convenio;
    @Column(nullable = true)
    private String seguro;
    @ManyToOne
    private Professor orientador;
    @Column(nullable = false)
    @NotNull(message = "Desculpe! O campo nome do supervisor é obrigatório")
    private String nomeSupervisor;
    @Column(nullable = false)
    @NotNull(message = "Desculpe! O campo email do supervisor é obrigatório")
    private String emailSupervisor;
    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private PeriodoLetivo periodo;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne
    private RelatorioFinal relatorioFinal;
    @ManyToOne
    private Aluno aluno;
    @OneToMany(mappedBy = "estagio")
    private List<FrequenciaMensalEstagio> frequenciasMensais;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(String termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFichaCadastroEmpresa() {
        return fichaCadastroEmpresa;
    }

    public void setFichaCadastroEmpresa(String fichaCadastroEmpresa) {
        this.fichaCadastroEmpresa = fichaCadastroEmpresa;
    }

    public boolean isConvenio() {
        return convenio;
    }

    public void setConvenio(boolean convenio) {
        this.convenio = convenio;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public String getNomeSupervisor() {
        return nomeSupervisor;
    }

    public void setNomeSupervisor(String nomeSupervisor) {
        this.nomeSupervisor = nomeSupervisor;
    }

    public String getEmailSupervisor() {
        return emailSupervisor;
    }

    public void setEmailSupervisor(String emailSupervisor) {
        this.emailSupervisor = emailSupervisor;
    }

    public PeriodoLetivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLetivo periodo) {
        this.periodo = periodo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RelatorioFinal getRelatorioFinal() {
        return relatorioFinal;
    }

    public void setRelatorioFinal(RelatorioFinal relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }

    public List<FrequenciaMensalEstagio> getFrequenciasMensais() {
        return frequenciasMensais;
    }

    public void setFrequenciasMensais(List<FrequenciaMensalEstagio> frequenciasMensais) {
        this.frequenciasMensais = frequenciasMensais;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void add(FrequenciaMensalEstagio freqMensal) {
        frequenciasMensais.add(freqMensal);
    }   

    @Override
    public String toString() {
        return "Estagio{" + "empresa=" + empresa + ", periodo=" + periodo + ", aluno=" + aluno + '}';
    }
}
