package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Bruna
 */
@NamedQueries({
    @NamedQuery(name = "Solicitacao.findBySolicitante", query = "SELECT s FROM Solicitacao s where s.solicitante.id = :pId order by s.id DESC"),
    @NamedQuery(name = "Solicitacao.findByCurso", query="SELECT s FROM Solicitacao s WHERE s.solicitante.curso.id = :idCurso  order by s.id  DESC"),
    @NamedQuery(name = "Solicitacao.findByCursos", query="SELECT s FROM Solicitacao s WHERE s.solicitante.curso IN (:cursoList) order by s.id DESC"),
    @NamedQuery(name = "Solicitacao.findByPeriodo", query = "SELECT s FROM Solicitacao s WHERE s.periodo.id = :idPeriodo order by s.id DESC"),
    @NamedQuery(name = "Solicitacao.listByPeriodo", query = "SELECT s.atividade.codigo,s.atividade.nome, COUNT(s.atividade), s.atividade.grupo.codigo, s.atividade.grupo.nome FROM Solicitacao s WHERE s.statusAtual = 'DEFERIDA' AND  s.periodo.id = :idPeriodo GROUP BY s.atividade.codigo, s.atividade.nome, s.atividade.grupo.codigo, s.atividade.grupo.nome ORDER BY s.atividade.codigo"),
    @NamedQuery(name = "Solicitacao.countBySolicitante", query = "SELECT SUM(s.horasComputadas) FROM Solicitacao s WHERE s.solicitante.id = :pId AND s.statusAtual = :status"),
    @NamedQuery(name = "Solicitacao.countByGrupo", query = "SELECT SUM(s.horasComputadas) FROM Solicitacao s WHERE s.solicitante.id = :idAluno AND s.statusAtual = :status AND s.atividade.grupo.id = :idGrupo"),
    @NamedQuery(name = "Solicitacao.findHistorico", query="SELECT SUM(s.horasComputadas), s.atividade.codigo, s.atividade.nome, s.atividade.grupo.codigo, s.atividade.grupo.nome, s.periodo.codigo  FROM Solicitacao s WHERE s.solicitante.id = :idAluno AND s.statusAtual = :statusAtual GROUP BY s.atividade.codigo, s.atividade.nome, s.atividade.grupo.codigo, s.atividade.grupo.nome, s.periodo.codigo ORDER BY s.atividade.grupo.codigo"),
        
    @NamedQuery(name = "Solicitacao.findHistoricoCategoria", query="SELECT s.solicitante.id, s.solicitante.usuario.id, s.solicitante.matricula, s.solicitante.usuario.nome,"
        + "SUM(CASE WHEN s.periodo.id = :idPeriodo THEN (CASE WHEN s.atividade.categoria = 'PESQUISA' THEN s.horasComputadas ELSE 0 END) ELSE 0 END), SUM(CASE WHEN s.periodo.id = :idPeriodo THEN (CASE WHEN s.atividade.categoria = 'ENSINO' THEN s.horasComputadas ELSE 0 END) ELSE 0 END), SUM(CASE WHEN s.periodo.id = :idPeriodo THEN (CASE WHEN s.atividade.categoria = 'EXTENSAO' THEN s.horasComputadas ELSE 0 END) ELSE 0 END)"
        + ", s.solicitante.curso.codigo, s.solicitante.curso.curso, s.solicitante.curso.id FROM Solicitacao s WHERE s.solicitante IN (:alunos) AND s.statusAtual = 'DEFERIDA' GROUP BY s.solicitante.id, s.solicitante.usuario.id, s.solicitante.matricula, s.solicitante.usuario.nome, s.solicitante.curso.codigo, s.solicitante.curso.curso, s.solicitante.curso.id ORDER BY s.solicitante.usuario.nome")
})
@Entity
public class Solicitacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_atividade")
    private Atividade atividade;
    @Column(name = "tx_descricao", nullable = false)
    private String descricao;
    @Column(name = "hr_qntd", nullable = false)
    private int qntd_horas;
    @Column(name = "hr_computadas")
    private int horasComputadas;
    @Column(name = "tx_observacoes")
    private String observacoes;
    @ElementCollection(targetClass = String.class)
    private List<String> anexos;
    @OneToMany
    private List<Mudanca> mudancas;
    @ManyToOne
    private Aluno solicitante;
    @Transient
    private Date dataAlteracao;
    @Enumerated(EnumType.STRING)
    private Status statusAtual;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataInicio;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataTermino;
    @ManyToOne
    private PeriodoLetivo periodo;

    public Solicitacao() {
        this.horasComputadas = 0;
        this.mudancas = new ArrayList<Mudanca>();
    }

    public Status getStatusAtual() {
        return this.statusAtual;
    }

    public void setStatusAtual(Status statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQntd_horas() {
        return qntd_horas;
    }

    public void setQntd_horas(int qntd_horas) {
        this.qntd_horas = qntd_horas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    public List<Mudanca> getMudancas() {
        return mudancas;
    }

    public void setMudancas(List<Mudanca> mudancas) {
        this.mudancas = mudancas;
    }

    public Aluno getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Aluno solicitante) {
        this.solicitante = solicitante;
    }

    public int getHorasComputadas() {
        return horasComputadas;
    }

    public void setHorasComputadas(int horasComputadas) {
        this.horasComputadas = horasComputadas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public PeriodoLetivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLetivo periodo) {
        this.periodo = periodo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitacao)) {
            return false;
        }
        Solicitacao other = (Solicitacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Solicitacao{" + "id=" + id + ", atividade=" + atividade + ", descricao=" + descricao + ", qntd_horas=" + qntd_horas + ", observacoes=" + observacoes + ", anexos=" + anexos + ", mudancas=" + mudancas + ", solicitante=" + solicitante + ", dataAlteracao=" + dataAlteracao + ", statusAtual=" + statusAtual + ", dataInicio=" + dataInicio + ", dataTermino=" + dataTermino + '}';
    }

    public void adicionaMudanca(Mudanca mudanca) {
        this.mudancas.add(mudanca);
    }

    public String getDataAlteracao() {
        Date date = mudancas.get(mudancas.size() - 1).getDataMudanca();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        return formatador.format(date);
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }
}
