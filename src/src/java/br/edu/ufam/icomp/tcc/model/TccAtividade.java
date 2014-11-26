/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.model;

import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tccatividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccAtividade.findAll", query = "SELECT t FROM TccAtividade t"),
    @NamedQuery(name = "TccAtividade.findByPeriodo", query = "SELECT t FROM TccAtividade t WHERE t.periodo.id = :idPeriodo"),
    @NamedQuery(name = "TccAtividade.findById", query = "SELECT t FROM TccAtividade t WHERE t.id = :id"),
    @NamedQuery(name = "TccAtividade.findByDatalimite", query = "SELECT t FROM TccAtividade t WHERE t.datalimite = :datalimite"),
    @NamedQuery(name = "TccAtividade.findByDataprorrogacao", query = "SELECT t FROM TccAtividade t WHERE t.dataprorrogacao = :dataprorrogacao"),
    @NamedQuery(name = "TccAtividade.findByDescricao", query = "SELECT t FROM TccAtividade t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TccAtividade.findByEstado", query = "SELECT t FROM TccAtividade t WHERE t.estado = :estado"),
    @NamedQuery(name = "TccAtividade.findByResponsavel", query = "SELECT t FROM TccAtividade t WHERE t.responsavel = :responsavel")})
public class TccAtividade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_atividade", sequenceName="tcc_atividade_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_atividade", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "datalimite", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datalimite;
    @Column(name = "dataprorrogacao", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataprorrogacao;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao")
    private String descricao;
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado", nullable = false)
    private String estado;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "responsavel", nullable = false)
    private String responsavel;
    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private PeriodoLetivo periodo;

    public TccAtividade() {
    }

    public TccAtividade(Long id) {
        this.id = id;
    }

    public TccAtividade(Long id, Date datalimite, Date dataprorrogacao, String descricao, String estado, String responsavel, PeriodoLetivo periodo) {
        this.id = id;
        this.datalimite = datalimite;
        this.dataprorrogacao = dataprorrogacao;
        this.descricao = descricao;
        this.estado = estado;
        this.responsavel = responsavel;
        this.periodo = periodo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatalimite() {
        return datalimite;
    }

    public void setDatalimite(Date datalimite) {
        this.datalimite = datalimite;
    }

    public Date getDataprorrogacao() {
        return dataprorrogacao;
    }

    public void setDataprorrogacao(Date dataprorrogacao) {
        this.dataprorrogacao = dataprorrogacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
    
    public PeriodoLetivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLetivo periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return descricao;
    }
    
}
