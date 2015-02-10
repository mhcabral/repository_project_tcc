package br.edu.ufam.icomp.tcc.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TAMMY
 */

@Entity
@Table(name = "tccnotas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccNotas.findAll", query = "SELECT t FROM TccNotas t"),
    @NamedQuery(name = "TccNotas.findById", query = "SELECT t FROM TccNotas t WHERE t.id = :id"),
    @NamedQuery(name = "TccNotas.findByTcc", query = "SELECT t FROM TccNotas t WHERE t.tcctcc.id = :idTccTcc")
})
public class TccNotas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_notas", sequenceName="tcc_notas_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_notas", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota1")
    private float nota1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota2")
    private float nota2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota3")
    private float nota3;
    @OneToOne
    @JoinColumn(name = "id_tcc_tcc")
    private TccTcc tcctcc;
    @Transient
    private float media;

    public TccNotas() {
    }

    public TccNotas(Long id) {
        this.id = id;
    }

    public TccNotas(Long id, float nota1, float nota2, float nota3) {
        this.id = id;
        this.nota1 = nota2;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TccTcc getTcctcc() {
        return tcctcc;
    }

    public void setTcctcc(TccTcc tcctcc) {
        this.tcctcc = tcctcc;
    }
    
    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = nota1;
    }

    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
    }

    public float getNota3() {
        return nota3;
    }

    public void setNota3(float nota3) {
        this.nota3 = nota3;
    }

    public float getMedia() {
        return ((this.nota1 + this.nota2 + (2*this.nota3))/4);
    }
}