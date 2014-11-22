package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Bruna
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PeriodoLetivo.findAllOrderByCodigo", query = "SELECT c FROM PeriodoLetivo c ORDER BY c.codigo DESC"),
    @NamedQuery(name = "PeriodoLetivo.findLetivoAtivo", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicio <= :dtAtual AND :dtAtual <= c.dtFim"),
    @NamedQuery(name = "PeriodoLetivo.findEstagioAtivo", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicioEstagio <= :dtAtual AND :dtAtual <= c.dtFimEstagio"),
    @NamedQuery(name = "PeriodoLetivo.listLetivoAnterior", query = "SELECT c FROM PeriodoLetivo c WHERE :dtAtual > c.dtFim ORDER BY c.dtFim DESC"),
    @NamedQuery(name = "PeriodoLetivo.listLetivoIniciado", query = "SELECT c FROM PeriodoLetivo c WHERE :dtAtual > c.dtInicio ORDER BY c.codigo DESC"),
    @NamedQuery(name = "PeriodoLetivo.findMatriculaAtivo", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicioMatricula <= :dtAtual AND :dtAtual <= c.dtFimMatricula"),
    @NamedQuery(name = "PeriodoLetivo.findById", query = "SELECT c FROM PeriodoLetivo c WHERE c.id = :id"),
    @NamedQuery(name = "PeriodoLetivo.findByData", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicio = :data"),
    @NamedQuery(name = "PeriodoLetivo.findByCodigo", query = "SELECT c FROM PeriodoLetivo c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "PeriodoLetivo.findProximoPeriodo", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicio >= :dtAtual ORDER BY c.dtInicio"),
    @NamedQuery(name = "PeriodoLetivo.exists", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicio= :dtInicio AND c.dtFim= :dtFim"),
    @NamedQuery(name = "PeriodoLetivo.existsMatricula", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicioMatricula= :dtInicioMatricula AND c.dtFimMatricula = :dtFimMatricula"),
    @NamedQuery(name = "PeriodoLetivo.existsEstagio", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicioEstagio= :dtInicioEstagio AND c.dtFimEstagio = :dtFimEstagio"),
    @NamedQuery(name = "PeriodoLetivo.checkLetivo", query = "SELECT c FROM PeriodoLetivo c WHERE c.dtInicio BETWEEN :dtInicio AND :dtFim OR c.dtFim BETWEEN :dtInicio AND :dtFim OR :dtInicio BETWEEN c.dtInicio AND c.dtFim OR :dtFim BETWEEN c.dtInicio AND c.dtFim")
})
public class PeriodoLetivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_periodo_letivo", sequenceName = "periodo_letivo_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_periodo_letivo", strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtFim;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtInicioMatricula;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtFimMatricula;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtInicioEstagio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtFimEstagio;    
    @Column(nullable = false)
    private String codigo;
    @Transient
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public Date getDtInicioMatricula() {
        return dtInicioMatricula;
    }

    public void setDtInicioMatricula(Date dtInicioMatricula) {
        this.dtInicioMatricula = dtInicioMatricula;
    }

    public Date getDtFimMatricula() {
        return dtFimMatricula;
    }

    public void setDtFimMatricula(Date dtFimMatricula) {
        this.dtFimMatricula = dtFimMatricula;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        Date atual = new Date();
        if (dtInicio.after(atual) || dtFim.before(atual)) {
            return "Inativo";
        } else {
            return "Ativo";
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoLetivo)) {
            return false;
        }
        PeriodoLetivo other = (PeriodoLetivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigo;
    }

    /**
     * @return the dtInicioEstagio
     */
    public Date getDtInicioEstagio() {
        return dtInicioEstagio;
    }

    /**
     * @param dtInicioEstagio the dtInicioEstagio to set
     */
    public void setDtInicioEstagio(Date dtInicioEstagio) {
        this.dtInicioEstagio = dtInicioEstagio;
    }

    /**
     * @return the dtFimEstagio
     */
    public Date getDtFimEstagio() {
        return dtFimEstagio;
    }

    /**
     * @param dtFimEstagio the dtFimEstagio to set
     */
    public void setDtFimEstagio(Date dtFimEstagio) {
        this.dtFimEstagio = dtFimEstagio;
    }
}
