
package br.edu.ufam.icomp.tcc.model;

import java.io.Serializable;
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
    @NamedQuery(name = "TccSolicitacao.findByProfessor", query = "SELECT t FROM TccSolicitacao t WHERE t.tcc.professor.id = :idProfessor")
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
    @Size(min = 1, max = 1024)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tipo") //{Aproveitamento, Definir TCC}
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "id_tcc")
    private TccTcc tcc;
    @Size(min = 1, max = 1024)
    @Column(name = "observacao")
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TccTcc getTcc() {
        return tcc;
    }

    public void setTcc(TccTcc tcc) {
        this.tcc = tcc;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
    
    
    
}
