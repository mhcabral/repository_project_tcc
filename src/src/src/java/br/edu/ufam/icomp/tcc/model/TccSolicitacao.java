
package br.edu.ufam.icomp.tcc.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "tccsolicitacao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccSolicitacao.findAll", query = "SELECT t FROM TccSolicitacao t"),
    @NamedQuery(name = "TccSolicitacao.findById", query = "SELECT t FROM TccSolicitacao t WHERE t.id= :id"),
    @NamedQuery(name = "TccSolicitacao.findByProfessor", query = "SELECT t FROM TccSolicitacao t WHERE t.tccTcc.professor.id = :idProfessor")
})
public class TccSolicitacao implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_solicitacao", sequenceName="tcc_solicitacao_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_solicitacao", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado")
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_atividade")
    private TccAtividade atividade;
    @ManyToOne
    private TccTcc tccTcc;
    @Size(min = 1, max = 1024)
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(mappedBy="tccSolicitacao")
    @Column(insertable = false, updatable = false)
    private List<TccAnexo> anexos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TccAtividade getAtividade() {
        return atividade;
    }

    public void setAtividade(TccAtividade atividade) {
        this.atividade = atividade;
    }

    public TccTcc getTccTcc() {
        return tccTcc;
    }

    public void setTccTcc(TccTcc tccTcc) {
        this.tccTcc = tccTcc;
    }

    public List<TccAnexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<TccAnexo> anexos) {
        this.anexos = anexos;
    }
    
    @Override
    public String toString() {
        return atividade.getDescricao();
    }
    
    
    
}
