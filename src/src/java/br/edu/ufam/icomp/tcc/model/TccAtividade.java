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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tccatividade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccAtividade.findAll", query = "SELECT t FROM TccAtividade t"),
    @NamedQuery(name = "TccAtividade.findByPeriodo", query = "SELECT t FROM TccAtividade t WHERE t.periodo.id = :idPeriodo ORDER BY t.datalimite, t.ordem"),
    @NamedQuery(name = "TccAtividade.findByAnexo", query = "SELECT t FROM TccAtividade t WHERE t.ordem in (1,3,5,8,12) AND t.periodo.id = :idPeriodo ORDER BY t.datalimite, t.ordem"),
    @NamedQuery(name = "TccAtividade.findById", query = "SELECT t FROM TccAtividade t WHERE t.id = :id"),
    @NamedQuery(name = "TccAtividade.findByDatalimite", query = "SELECT t FROM TccAtividade t WHERE t.datalimite = :datalimite"),
    @NamedQuery(name = "TccAtividade.findByDataprorrogacao", query = "SELECT t FROM TccAtividade t WHERE t.dataprorrogacao = :dataprorrogacao"),
    @NamedQuery(name = "TccAtividade.findByDescricao", query = "SELECT t FROM TccAtividade t WHERE t.descricao = :descricao"),
    @NamedQuery(name = "TccAtividade.findByResponsavel", query = "SELECT t FROM TccAtividade t WHERE t.responsavel = :responsavel"),
    @NamedQuery(name = "TccAtividade.findDataTema", query = "SELECT t.datalimite FROM TccAtividade t WHERE t.periodo.id = :idPeriodo AND t.ordem = 1 AND (t.datalimite >= CURRENT_DATE OR (t.dataprorrogacao is not null and t.dataprorrogacao >= CURRENT_DATE))")
})
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
    @Column(name = "dataprorrogacao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataprorrogacao;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Transient
    private String estado;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "responsavel", nullable = false)
    private String responsavel;
    @ManyToOne
    @JoinColumn(name = "id_periodo")
    private PeriodoLetivo periodo;
    @NotNull
    @Column(name = "ordem")
    private Integer ordem;

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
        Date atual = new Date();
        if (datalimite.before(atual) && (dataprorrogacao == null)) {
            estado = "Fechado";
        } else if (!(dataprorrogacao == null)) {
            if (dataprorrogacao.before(atual)) {
                estado = "Fechado";
            } else {
                estado = "Aberto";
            }
        } else {
            estado = "Aberto";
        }
        return estado;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer order) {
        this.ordem = order;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
    
}
