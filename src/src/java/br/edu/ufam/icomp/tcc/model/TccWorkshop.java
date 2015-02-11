/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mhcabral
 */
@Entity
@Table(name = "tccworkshop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccWorkshop.findAll", query = "SELECT a FROM TccWorkshop a"),
    @NamedQuery(name = "TccWorkshop.findById", query = "SELECT a FROM  TccWorkshop a WHERE a.id = :idWorkshop")
})
public class TccWorkshop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_workshop", sequenceName="tcc_workshop_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_workshop", strategy= GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data;
    @OneToOne
    @JoinColumn(name = "id_tcc_tcc")
    private TccTcc tcctcc;
    @OneToOne
    @JoinColumn(name = "id_tcc_locais")
    private TccLocais tcclocais;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)   
    @Column(name = "avaliador1")
    private String avaliador1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)   
    @Column(name = "avaliador2")
    private String avaliador2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TccTcc getTcctcc() {
        return tcctcc;
    }

    public void setTcctcc(TccTcc tcctcc) {
        this.tcctcc = tcctcc;
    }

    public TccLocais getTcclocais() {
        return tcclocais;
    }

    public void setTcclocais(TccLocais tcclocais) {
        this.tcclocais = tcclocais;
    }

    public String getAvaliador1() {
        return avaliador1;
    }

    public void setAvaliador1(String avaliador1) {
        this.avaliador1 = avaliador1;
    }

    public String getAvaliador2() {
        return avaliador2;
    }

    public void setAvaliador2(String avaliador2) {
        this.avaliador2 = avaliador2;
    }

    @Override
    public String toString() {
        return id + "";
    }
    
    
}
