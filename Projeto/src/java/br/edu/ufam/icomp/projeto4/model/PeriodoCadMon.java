package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Janderson
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PeriodoCadMon.listAll", query = "SELECT c FROM PeriodoCadMon c "),
    @NamedQuery(name = "PeriodoCadMon.findById", query = "SELECT c FROM PeriodoCadMon c WHERE c.id= :id"),
    @NamedQuery(name = "PeriodoCadMon.exists", query = "SELECT c FROM PeriodoCadMon c WHERE  c.curso= :curso AND c.dtInicio= :dtInicio AND c.dtFim= :dtFim"),    
    @NamedQuery(name = "PeriodoCadMon.checkDate", query = "SELECT c FROM PeriodoCadMon c WHERE c.curso= :curso AND c.dtInicio< :dtInicio AND c.dtFim>:dtFim"),        
    @NamedQuery(name = "PeriodoCadMon.existsAnother", query = "SELECT c FROM PeriodoCadMon c WHERE  c.curso= :curso AND c.dtInicio= :dtInicio AND c.dtFim= :dtFim AND c.id<>:id"),    
    @NamedQuery(name = "PeriodoCadMon.checkAnotherDate", query = "SELECT c FROM PeriodoCadMon c WHERE c.curso= :curso AND c.dtInicio< :dtInicio AND c.dtFim>:dtFim AND  c.id<>:id"),            
    @NamedQuery(name = "PeriodoCadMon.findByCurso", query = "SELECT c FROM PeriodoCadMon c WHERE c.curso = :curso"),
    @NamedQuery(name = "PeriodoCadMon.findAtivo", query = "SELECT c FROM PeriodoCadMon c WHERE c.dtInicio <= :dtAtual AND :dtAtual <= c.dtFim")
})
public class PeriodoCadMon implements Serializable{
    
    private static final long serialVersionUID = 1L;   

    @Id
    @SequenceGenerator(name = "id_periodo_cad_mon", sequenceName = "periodo_cad_mon_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_periodo_cad_mon", strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(optional=false)
    @NotNull(message="O Curso é obrigatório")
    private Curso curso;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable=false)
    @NotNull(message="A Data de Início é obrigatória")
    private Date dtInicio;    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable=false)
    @NotNull(message="A Data de Término é obrigatória")
    private Date dtFim;
    
    @Transient
    private String status;

    @Override
    public String toString() {
        return "PeriodoCadMon{" + "id=" + id + ", curso=" + curso + ", dtInicio=" + dtInicio + ", dtFim=" + dtFim + '}';
    }  

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the dtInicio
     */
    public Date getDtInicio() {
        return dtInicio;
    }

    /**
     * @param dtInicio the dtInicio to set
     */
    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    /**
     * @return the dtFim
     */
    public Date getDtFim() {
        return dtFim;
    }

    /**
     * @param dtFim the dtFim to set
     */
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
}
