package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author Leonardo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PeriodoInsMon.listAll", query = "SELECT c FROM PeriodoInsMon c "),
    @NamedQuery(name = "PeriodoInsMon.listAtivos", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio <= :dtAtual AND :dtAtual <= c.dtFim"),
    @NamedQuery(name = "PeriodoInsMon.findById", query = "SELECT c FROM PeriodoInsMon c WHERE c.id= :id"),
    @NamedQuery(name = "PeriodoInsMon.findProximoPeriodo", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio >= :dtAtual ORDER BY c.dtInicio"),
    @NamedQuery(name = "PeriodoInsMon.exists", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio= :dtInicio AND c.dtFim= :dtFim"),
    @NamedQuery(name = "PeriodoInsMon.checkDate1", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio < :dtInicio AND c.dtFim > :dtFim AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate2", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio > :dtInicio AND c.dtFim < :dtFim AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate3", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio > :dtInicio AND c.dtInicio < :dtFim AND c.dtFim > :dtFim AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate4", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtFim < :dtFim AND c.dtInicio < :dtInicio AND c.dtFim > :dtInicio AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate5", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio = :dtInicio AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate6", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtFim = :dtFim AND c.id != :id"),
    @NamedQuery(name = "PeriodoInsMon.checkDate7", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio < :dtInicio AND c.dtFim > :dtFim"),
    @NamedQuery(name = "PeriodoInsMon.checkDate8", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio > :dtInicio AND c.dtFim < :dtFim"),
    @NamedQuery(name = "PeriodoInsMon.checkDate9", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio > :dtInicio AND c.dtInicio < :dtFim AND c.dtFim > :dtFim"),
    @NamedQuery(name = "PeriodoInsMon.checkDate10", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtFim < :dtFim AND c.dtInicio < :dtInicio AND c.dtFim > :dtInicio"),
    @NamedQuery(name = "PeriodoInsMon.checkDate11", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtInicio = :dtInicio"),
    @NamedQuery(name = "PeriodoInsMon.checkDate12", query = "SELECT c FROM PeriodoInsMon c WHERE c.dtFim = :dtFim")})
public class PeriodoInsMon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_periodo_ins_mon", sequenceName = "periodo_ins_mon_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_periodo_ins_mon", strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date dtFim;
    @Transient
    private String status;

    @Override
    public String toString() {
        return "Periodo{" + "id=" + id + ", dtInicio=" + dtInicio + ", dtFim=" + dtFim + '}';
        //return "Periodo{" + "id=" + id + ", dtInicio=" + dtInicio + ", dtFim=" + dtFim + '}';
    }

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

    public String getStatus() {
        Date atual = new Date();
        if (dtInicio.after(atual) || dtFim.before(atual)) {
            return "Inativo";
        } else {
            return "Ativo";
        }
    }

    public String validaData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date hoje = new Date();
        if ((hoje.after(dtInicio) || hoje.equals(dtInicio)) && (hoje.equals(dtFim) || hoje.before(dtFim))) {
            return "ativo";
        } else if (hoje.before(dtInicio)) {
            return "antes";
        } else if (hoje.after(dtFim)) {
            return "depois";
        }
        return "não declarado";
    }
}
