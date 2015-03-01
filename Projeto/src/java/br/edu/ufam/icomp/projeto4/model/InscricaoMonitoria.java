package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

/**
 *
 * @author leonardo
 */
@NamedQueries({
    @NamedQuery(name = "InscricaoMonitoria.findByMonitoria", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.id = :id"),
    @NamedQuery(name = "InscricaoMonitoria.listByAlunoStatus", query = "SELECT i FROM InscricaoMonitoria i where i.inscrito.id = :idAluno AND i.statusAtual = :status"),
    //início análise de solicitações
    @NamedQuery(name = "InscricaoMonitoria.listAllNova", query = "SELECT c FROM InscricaoMonitoria c where c.statusAtual='NOVA'"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoNova", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND  i.statusAtual='NOVA'"),
    @NamedQuery(name = "InscricaoMonitoria.findByDiscipĺinaNova", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.disciplina.id = :idDisciplina  AND  i.statusAtual='NOVA'"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoDisciplinaNova", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.monitoria.disciplina.id = :idDisciplina  AND  i.statusAtual='NOVA'"),
    //---fim análise de solicitações
    //Envio de Documentos pelo aluno
    @NamedQuery(name = "InscricaoMonitoria.findEnvio", query = "SELECT i FROM InscricaoMonitoria i WHERE i.inscrito.usuario.id=:idUser AND i.statusAtual='DEFERIDA' AND i.monitoria.periodo.dtInicio <= current_date() AND i.monitoria.periodo.dtFim >= current_date()"),
    //Fim - Envio de Documentos pelo aluno 
    @NamedQuery(name = "InscricaoMonitoria.findByCurso", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso"),
    @NamedQuery(name = "InscricaoMonitoria.findByProfessor", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.professor.id = :idCurso"),
    @NamedQuery(name = "InscricaoMonitoria.findByDiscipĺina", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "InscricaoMonitoria.findByAluno", query = "SELECT i FROM InscricaoMonitoria i where i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoProfessor", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.monitoria.professor.id = :idProfessor"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoDisciplina", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.monitoria.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoAluno", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByProfessorDisciplina", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.professor.id = :idProfessor AND i.monitoria.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "InscricaoMonitoria.findByProfessorAluno", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.professor.id = :idProfessor AND i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByDisciplinaAluno", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.disciplina = :idDisciplina AND i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoProfessorDisciplina", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.monitoria.professor.id = :idProfessor AND i.monitoria.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoProfessorAluno", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.curso.id = :idCurso AND i.monitoria.professor.id = :idProfessor AND i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByProfessorDisciplinaAluno", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.professor.id = :idProfessor AND i.monitoria.disciplina.id = :idDisciplina AND i.inscrito.id = :idAluno"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoDisciplinaAluno", query = "SELECT i FROM InscricaoMonitoria i where i.inscrito.id = :idAluno AND i.monitoria.curso.id = :idCurso AND i.monitoria.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "InscricaoMonitoria.findByCursoProfessorDisciplinaAluno", query = "SELECT i FROM InscricaoMonitoria i where i.inscrito.id = :idAluno AND i.monitoria.curso.id = :idCurso AND i.monitoria.disciplina.id = :idDisciplina AND i.monitoria.professor.id = :idProfessor"),
    @NamedQuery(name = "InscricaoMonitoria.findByInscrito", query = "SELECT i FROM InscricaoMonitoria i where i.inscrito.id = :id"),
    @NamedQuery(name = "InscricaoMonitoria.exist", query = "SELECT i FROM InscricaoMonitoria i where i.monitoria.id = :idMonitoria AND  i.inscrito.id = :idAluno"),
    //@NamedQuery(name = "InscricaoMonitoria.findByDisci", query="SELECT i FROM InscricaoMonitoria i WHERE i.solicitante.curso IN (:cursoList)"),
    @NamedQuery(name = "InscricaoMonitoria.countByInscrito", query = "SELECT SUM(i.monitoriasComputadas) FROM InscricaoMonitoria i WHERE i.inscrito.id = :id AND i.statusAtual = :status"),
    @NamedQuery(name = "InscricaoMonitoria.listByAlunosCurso", query="SELECT i FROM InscricaoMonitoria i WHERE i.inscrito IN (:alunoList)")
})
@Entity
public class InscricaoMonitoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_inscricao_monitoria", sequenceName = "inscricao_monitoria_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_inscricao_monitoria", strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private boolean bolsista;
    @Column(nullable = true)
    private String banco;
    @Column(nullable = true)
    private String agencia;
    @Column(nullable = true)
    private String conta;
    @Column(nullable = true)
    private String comprovante;
    @Column(nullable = true)
    private String historico;        
    @OneToOne
    private RelatorioFinal relatorioFinal;
    @ElementCollection(targetClass = String.class)
    private List<String> frequencia;
    @ManyToOne
    @JoinColumn(name = "id_monitoria")
    private Monitoria monitoria;
    @ManyToOne
    private Aluno inscrito;
    @Column(name = "mon_computadas")
    private int monitoriasComputadas;
    @Enumerated(EnumType.STRING)
    private Status statusAtual;
    
    @OneToMany(mappedBy = "inscricao")
    private List<FrequenciaMensal> frequenciasMensais;

    public InscricaoMonitoria() {
        this.monitoriasComputadas = 0;
        this.frequenciasMensais = new ArrayList<FrequenciaMensal>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBolsista() {
        return bolsista;
    }

    public void setBolsista(boolean bolsista) {
        this.bolsista = bolsista;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public List<String> getfrequencia() {
        return frequencia;
    }

    public void setComprovane(String comprovante) {
        this.comprovante = comprovante;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

//    public void setAnexos(List<String> anexos) {
//        this.anexos = anexos;
//    }
    public Monitoria getMonitoria() {
        return monitoria;
    }

    public void setMonitoria(Monitoria monitoria) {
        this.monitoria = monitoria;
    }

    public Aluno getInscrito() {
        return inscrito;
    }

    public void setInscrito(Aluno inscrito) {
        this.inscrito = inscrito;
    }

    public int getMonitoriasComputadas() {
        return monitoriasComputadas;
    }

    public void setMonitoriasComputadas(int monitoriasComputadas) {
        this.monitoriasComputadas = monitoriasComputadas;
    }

    public Status getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(Status statusAtual) {
        this.statusAtual = statusAtual;
    }

    public List<FrequenciaMensal> getFrequenciasMensais() {
        return frequenciasMensais;
    }

    public void setFrequenciasMensais(List<FrequenciaMensal> frequenciasMensais) {
        this.frequenciasMensais = frequenciasMensais;
    }
    
    public void add(FrequenciaMensal freqMensal){
        frequenciasMensais.add(freqMensal);
    }

    @Override
    public String toString() {
        return "InscricaoMonitoria{" + "id=" + id + ", bolsista=" + bolsista + ", banco=" + banco + ", agencia=" + agencia + ", conta=" + conta + ", comprovante=" + comprovante + ", historico=" + historico + ", frequencia=" + frequencia + ", monitoria=" + monitoria + ", inscrito=" + inscrito + ", monitoriasComputadas=" + monitoriasComputadas + ", statusAtual=" + statusAtual + '}';
    }

    /**
     * @return the relatorioFinal
     */
    public RelatorioFinal getRelatorioFinal() {
        return relatorioFinal;
    }

    /**
     * @param relatorioFinal the relatorioFinal to set
     */
    public void setRelatorioFinal(RelatorioFinal relatorioFinal) {
        this.relatorioFinal = relatorioFinal;
    }

    public String getComprovante() {
        return comprovante;
    }

    public String getHistorico() {
        return historico;
    }
    
    
}
